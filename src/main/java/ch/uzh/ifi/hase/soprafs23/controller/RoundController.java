package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.rest.dto.RoundGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoundController {
    private final LobbyService lobbyService;
    private final UserService userService;

    RoundController(LobbyService lobbyService, UserService userService) {
        this.lobbyService = lobbyService;
        this.userService = userService;
    }

    /**
     * GET /lobbies/{id}/categories
     * get 4 possible categories to choose from
     **/
    @GetMapping("/lobbies/{lobbyId}/categories")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RoundGetDTO getCategories(@PathVariable Long lobbyId, @RequestBody UserPostDTO userPostDTO) {
        //authentication
        lobbyService.isUserTokenInLobby(userPostDTO.getToken(), lobbyService.getLobby(lobbyId));

        //fetch & return the round
        Lobby lobby = lobbyService.getLobby(lobbyId);
        return DTOMapper.INSTANCE.convertRoundEntityToRoundGetDTO(lobby.getRound());
    }

    /**
     * PUT /lobbies/{id}/categories
     * send in the category vote
     **/
    @PutMapping("/lobbies/{lobbyId}/categories")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void get(@PathVariable Long lobbyId) {

        //fetch lobby
        Lobby lobby = lobbyService.getLobby(lobbyId);
    }



}