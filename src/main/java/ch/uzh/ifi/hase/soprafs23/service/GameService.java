package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final LobbyRepository lobbyRepository;
    private final LobbyService lobbyService;


    @Autowired
    public GameService(UserService userService, @Qualifier("lobbyRepository") LobbyRepository lobbyRepository, LobbyService lobbyService) {
        this.userService = userService;
        this.lobbyRepository = lobbyRepository;
        this.lobbyService = lobbyService;
    }

    public void evaluateAnswers(Long lobbyId) {
        List<User> users = lobbyService.getUsersFromLobby(lobbyId);
        Round round = lobbyService.getLobby(lobbyId).getRound();
        round.setTimerOver(true);
        // Filter users who answered correctly
        List<User> correctUsers = new ArrayList<>();
        for (User user : users) {
            //if user got the right answer
            if (Objects.equals(user.getAnswerIndex(), round.getRightAnswer())) {
                correctUsers.add(user);
            }
            //users that are wrong get -1
            else {
                if(user.getStepState() != 0) user.updateStepState(-1L);
            }
        }

        // Sort the correct users based on time taken to answer
        correctUsers.sort(Comparator.comparingDouble(User::getTime));

        // Assign scores based on position in the sorted list
        // currently, first one advances 4, 2nd advances 3, 3rd advances 1, last gets thrown back -1
        int numUsers = correctUsers.size();
        for (int i = 0; i < numUsers; i++) {
            User user = correctUsers.get(i);
            switch (i) {
                case 0 -> user.updateStepState(3L);
                case 1 -> user.updateStepState(2L);
                case 2 -> user.updateStepState(1L);
                default -> {
                    if (user.getStepState() != 0) user.updateStepState(-1L);
                }
            }
        }
    }
}
