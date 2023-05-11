package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.Categories;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.rest.dto.RoundGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class RoundController {
    private final LobbyService lobbyService;
    private final UserService userService;
    private final RoundService roundService;
    private final QuestionService questionService;

    private final GameService gameService;

    RoundController(LobbyService lobbyService, UserService userService, RoundService roundService, QuestionService questionService, GameService gameService) {
        this.lobbyService = lobbyService;
        this.userService = userService;
        this.questionService = questionService;
        this.roundService= roundService;
        this.gameService = gameService;
    }
    //todo fix this comments and method names
    /**
     * GET /lobbies/{id}/categories
     * get 4 possible categories to choose from
     **/
    @GetMapping("/lobbies/{lobbyId}/rounds")
    @ResponseStatus(HttpStatus.OK)
    public RoundGetDTO getRoundInfo(@PathVariable Long lobbyId) {

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

        //fetch round and lobby
        Lobby lobby = lobbyService.getLobby(lobbyId);
        Round round = lobby.getRound();

        //in the Pathvariable, we get 1-4 as the chosen category, this is how we get which Category enum was meant with that.
        Categories category = round.getCategories().get(categoryId-1);

        //add Category enum to Category votes
        roundService.addCategoryVote(category, lobbyId);


        //if all votes have been taken the timer can be aborted
        if(round.getCategoryVotes().size() == lobby.getUserIds().size()){
            lobbyService.setTimerOver(lobbyId, true);
          roundService.chooseCategory(lobbyId);
        }
    }

    @PutMapping("/lobbies/{lobbyId}/answers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void receiveQuestionAnswers(@PathVariable Long lobbyId, @RequestBody UserPutDTO userPutDTO) {
        //authentication
        lobbyService.isUserTokenInLobby(userPutDTO.getToken(), lobbyService.getLobby(lobbyId));
        Long answerIndex = userPutDTO.getAnswerIndex();
        if (answerIndex < 1 || answerIndex > 4) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}

        //update User with the time he voted and the index of the answer
        userService.updateTimeAndAnswer(userPutDTO.getToken(), userPutDTO.getTime(), userPutDTO.getAnswerIndex());
        roundService.incVoteCount(lobbyId);

        List<Long> userIds = lobbyService.getLobby(lobbyId).getUserIds();

        if(roundService.getRound(lobbyId).getAnswerCount() == userIds.size()) {
            gameService.evaluateAnswers(lobbyId);
        }
    }

}