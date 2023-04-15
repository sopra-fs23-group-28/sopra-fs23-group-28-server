package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.Categories;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.rest.dto.RoundGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
     * receive in the category vote
     **/
    @PutMapping("/lobbies/{lobbyId}/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void receiveCategoryAnswers(@PathVariable Long lobbyId, @PathVariable int categoryId, @RequestBody UserPostDTO userPostDTO) {
        //authentication
        lobbyService.isUserTokenInLobby(userPostDTO.getToken(), lobbyService.getLobby(lobbyId));
        if (categoryId < 1 || categoryId > 4) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}

        //fetch round
        Round round = lobbyService.getLobby(lobbyId).getRound();

        //in the Pathvariable, we get 1-4 as the chosen category, this is how we get which Category enum was meant with that.
        Categories category = round.getCategories().get(categoryId-1);

        //TODO check if all categories were set

        //add Category enum to Category votes
        round.addCategoryVotes(category);
    }
}