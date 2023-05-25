package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.config.SocketService;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.DifficultyPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.startPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
public class LobbyController {
    private final LobbyService lobbyService;
    private final UserService userService;
    private final SocketService socketService;
    LobbyController(LobbyService lobbyService, UserService userService, SocketService socketService) {
        this.lobbyService = lobbyService;
        this.userService = userService;
        this.socketService = socketService;
    }

     /** POST /lobbies
     * the RequestBody consist only of a User token. This is why the userPostDTO is being used.
     **/
    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LobbyGetDTO createLobby(@RequestBody UserPostDTO userPostDTO) {
        //fetch user
        User internalUser = DTOMapper.INSTANCE.convertUserPostDTOtoUserEntity(userPostDTO);
        User user = userService.getUserByToken(internalUser.getToken());

        //create lobby
        Lobby lobby = lobbyService.createLobby(user);

        return DTOMapper.INSTANCE.convertLobbyEntityToLobbyGetDTO(lobby);
    }

     /**
     * PUT /lobbies/{id}/user
     * the RequestBody consist only of a User token. This is why the userPostDTO is being used.
     **/
    @PutMapping("/lobbies/{lobbyId}/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void joinLobby(@PathVariable Long lobbyId, @RequestBody UserPostDTO userPostDTO){
        //fetch user
        User internalUser = DTOMapper.INSTANCE.convertUserPostDTOtoUserEntity(userPostDTO);
        User user = userService.getUserByToken(internalUser.getToken());

        //fetch lobby
        Lobby lobby = lobbyService.getLobby(lobbyId);

        //join lobby
        lobbyService.joinLobby(lobbyId, user);
    }

    /**
     * PUT /lobbies/{lobbyId}/users/{userId} - leave a lobby
     * the RequestBody consist only of a User token. This is why the userPostDTO is being used.
     **/
    @PutMapping("/lobbies/{lobbyId}/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveLobby(@PathVariable Long lobbyId, @PathVariable Long id, @RequestBody UserPostDTO userPostDTO){

        //fetch user
        User internalUser = DTOMapper.INSTANCE.convertUserPostDTOtoUserEntity(userPostDTO);
        User user = userService.getUserByToken(internalUser.getToken());

        //fetch lobby
        Lobby lobby = lobbyService.getLobby(lobbyId);

        //leave lobby
        lobbyService.leaveLobby(lobbyId, user);
    }

    /**
     * PUT /lobbies/{lobbyId}/difficulties
     * the RequestBody consist only of a User token. This is why the userPostDTO is being used.
     **/
    @PutMapping("/lobbies/{lobbyId}/difficulties")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setDifficultyForLobby(@PathVariable Long lobbyId, @RequestBody DifficultyPutDTO difficultyPutDTO){
        //authentification
        userService.getUserByToken(difficultyPutDTO.getToken());
        socketService.sendMessageToRoom(lobbyId.toString(), "WHEEL", String.valueOf(difficultyPutDTO.getDifficultyWheelDegree()));
    }

     /**
     * GET /lobbies/{id}
     **/
    @GetMapping("/lobbies/{lobbyId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LobbyGetDTO lobbyInfo(@PathVariable Long lobbyId) {

        //fetch lobby
        Lobby lobby = lobbyService.getLobby(lobbyId);
        return DTOMapper.INSTANCE.convertLobbyEntityToLobbyGetDTO(lobby);
    }

     /**
     * START THE GAME
     **/
    @PutMapping("/lobbies/{lobbyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void startGame(@PathVariable Long lobbyId, @RequestBody startPostDTO startPostDTO){
        //fetch user
        User internalUser = DTOMapper.INSTANCE.convertStartPostDTOtoUserEntity(startPostDTO);
        User user = userService.getUserByToken(internalUser.getToken());

        //convert to internal representation to get maxSteps
        Lobby internalLobby = DTOMapper.INSTANCE.convertStartPostDTOtoLobbyEntity(startPostDTO);
        Long maxSteps = internalLobby.getMaxSteps();
        //fetch lobby
        Lobby lobby = lobbyService.getLobby(lobbyId);

        List<User> users = lobbyService.getUsersFromLobby(lobbyId);

        for(User iterateUser : users) {
            userService.resetSteps(iterateUser.getId());
        }

        //validate that creator is making request
        lobbyService.validate(lobby, user);

        //set maxSteps
        lobbyService.setMaxSteps(maxSteps, lobbyId);

        socketService.sendMessageToRoom(lobbyId.toString(), "GAMESTART", "GAMESTART");
    }

/**
 *
 * GET the current IP address under which the socket can be reached.
 * Believe me, there is absolutely no possibility to solve it otherwise.
 * */

    @GetMapping("/external-ip")
    @ResponseStatus(HttpStatus.OK)
    public String getExternalIP() {
        try {
            URL url = new URL("http://metadata.google.internal/computeMetadata/v1/instance/network-interfaces/0/access-configs/0/external-ip");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Metadata-Flavor", "Google");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching external IP";
        }
    }
}
