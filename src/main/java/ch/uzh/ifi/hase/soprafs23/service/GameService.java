package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.config.SocketService;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
        for (User user : users) {
            //if user got the right answer, did a -1 because round saves right answer from 0 to 3, while user has 1 to 4

            if (Objects.equals(user.getAnswerIndex()-1, round.getRightAnswer())) {
                correctUsers.add(user);
            }
            //users that are wrong get -1
            else {
                if(user.getStepState() != 0) userService.updateStepStateOfUser(-1L, user.getId());
            }
        }


        // Sort the correct users based on time taken to answer
        correctUsers.sort(Comparator.comparingDouble(User::getTime));
        System.out.println("ENTERING SWITCH");
        // Assign scores based on position in the sorted list
        // currently, first one advances 4, 2nd advances 3, 3rd advances 1, last gets thrown back -1
        int numUsers = correctUsers.size();
        for (int i = 0; i < numUsers; i++) {
            User user = correctUsers.get(i);
            switch (i) {
                case 0:
                    userService.updateStepStateOfUser(3L, user.getId());
                    System.out.println("USER GOT FIRST PLACE"+ user.getUsername());
                    break;
                case 1:
                    userService.updateStepStateOfUser(2L, user.getId());
                    System.out.println("USER GOT 2nd place"+ user.getUsername());
                    break;
                case 2:
                    userService.updateStepStateOfUser(1L, user.getId());
                    System.out.println("USER GOT 3rd place"+ user.getUsername());
                    break;
                default:
                    if (user.getStepState() != 0) userService.updateStepStateOfUser(-1L, user.getId());
                    System.out.println("USER sucks" + user.getUsername());
                    break;
            }
        }
        socketService.sendRigthAnswer(lobbyId, roundService.getRound(lobbyId).getRightAnswer()+1);
        if(isFinished(lobbyId)) socketService.sendFinish(lobbyId,"FINISH");
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
