package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.config.SocketService;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class GameService {
    private final UserService userService;
    private final LobbyService lobbyService;
    private final SocketService socketService;
    private final RoundService roundService;

    @Autowired
    public GameService(UserService userService, LobbyService lobbyService, SocketService socketService, RoundService roundService) {
        this.userService = userService;
        this.lobbyService = lobbyService;
        this.socketService = socketService;
        this.roundService = roundService;
    }

    public void evaluateAnswers(Long lobbyId) {

        List<User> users = lobbyService.getUsersFromLobby(lobbyId);
        Lobby lobby =lobbyService.getLobby(lobbyId);
        Round round = lobby.getRound();
        System.out.println("IN EVALUATE, boolean: " + round.isTimerOver());
        round.setTimerOver(true);
        // Filter users who answered correctly
        List<User> correctUsers = new ArrayList<>();
        List<User> incorrectUser = new ArrayList<>();
        for (User user : users) {
            //if user did not answer OR did answer wrong. answerIndex-1 because round saves right answer from 0 to 3, while user has 1 to 4
            if(user.getAnswerIndex()-1 != round.getRightAnswer()) incorrectUser.add(user);
            //if user got the right answer
            else correctUsers.add(user);
        }

        // Sort the correct/incorrect users based on time taken to answer and then merge them
        correctUsers.sort(Comparator.comparingDouble(User::getTime));
        incorrectUser.sort(Comparator.comparingDouble(User::getTime));
        correctUsers.addAll(incorrectUser);

        //reverse the Collection so that the switch statement can judge the loser at index 0
        Collections.reverse(correctUsers);

        // Assign scores based on position in the sorted list
        // currently, first one advances 4, 2nd advances 3, 3rd advances 1, last gets thrown back the amount that the last loser voted on
        int numUsers = correctUsers.size();
        for (int i = 0; i < numUsers; i++) {
            User user = correctUsers.get(i);
            //for the first in the aray, the loser
            if (i == 0) {
                Long punishmentSteps = lobbyService.getPunishmentSteps(lobbyId);
                if (user.getStepState() != 0) {
                    //take the minimum of users steps and punishment steps s.t. no negative steps happen
                    Long updateSteps = -Math.min(user.getStepState(), punishmentSteps);
                    userService.updateStepStateOfUser(updateSteps, user.getId());
                }
                socketService.sendMessageToRoom(lobbyId.toString(), "LOSER", user.getId().toString());
            }
            else if(incorrectUser.contains(user)) continue;
            //for all others
            else userService.updateStepStateOfUser(Long.valueOf(i)+2L, user.getId());
        }
        socketService.sendMessageToRoom(lobbyId.toString(), "ROUND", String.valueOf(roundService.getRound(lobbyId).getRightAnswer()+1));
        if(isFinished(lobbyId)) socketService.sendMessageToRoom(lobbyId.toString(),"FINISH","FINISH");
    }

    //checks whether a  user in a lobby has reached the maxSteps
    private boolean isFinished(Long lobbyId) {
        Lobby lobby = lobbyService.getLobby(lobbyId);
        List<User> users = lobbyService.getUsersFromLobby(lobbyId);

        for(User user : users) {
            if(user.getStepState() >= lobby.getMaxSteps()) return true;
        }
        return false;
    }
}
