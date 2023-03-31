package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.CamelColors;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class LobbyController {
    private final LobbyService lobbyService;
    LobbyController(LobbyService lobbyService) {this.lobbyService = lobbyService;}

    /** POST /lobbies
     * the RequestBody consist only of a User token. This is why the userPostDTO is being used.
     * */
    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LobbyGetDTO createLobby(@RequestBody UserPostDTO userPostDTO) {

        //TODO: this is mockData
        Lobby mockLobby = new Lobby();
        mockLobby.setCreatorId(42L);
        mockLobby.addPlayerId(2L);

        return DTOMapper.INSTANCE.convertLobbyEntityToLobbyGetDTO(mockLobby);
    }

    /**
     * PUT /lobbies/{id}/join
     * the RequestBody consist only of a User token. This is why the userPostDTO is being used.
     * */
    @PutMapping("/lobbies/{id}/join")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void joinLobby(@PathVariable Long lobbyId, @RequestBody UserPostDTO userPOSTDTO){
        //TODO
    }

    /** GET /lobbies{id}
     * */
    @GetMapping("/lobbies")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LobbyGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {

        //TODO: this is mockData
        Lobby mockLobby = new Lobby();
        mockLobby.setCreatorId(42L);
        mockLobby.addPlayerId(2L);

        return DTOMapper.INSTANCE.convertLobbyEntityToLobbyGetDTO(mockLobby);
    }
}
