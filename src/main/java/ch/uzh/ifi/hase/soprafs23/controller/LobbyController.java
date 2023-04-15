package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.startPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class LobbyController {
    private final LobbyService lobbyService;
    private final UserService userService;
    LobbyController(LobbyService lobbyService, UserService userService) {
        this.lobbyService = lobbyService;
        this.userService = userService;
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
     * PUT /lobbies/{id}/join
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
        lobbyService.joinLobby(lobby, user);
        //TODO send message to socket.io endpoint NEWUSER
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

        //validate that creator is making request
        lobbyService.validate(lobby, user);

        //set maxSteps
        lobbyService.setMaxSteps(maxSteps, lobby);


    }
}
