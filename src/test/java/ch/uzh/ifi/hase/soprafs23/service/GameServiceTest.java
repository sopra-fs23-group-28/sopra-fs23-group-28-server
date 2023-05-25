package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.config.SocketService;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class GameServiceTest {
    @Mock
    private LobbyService lobbyService;
    @Mock
    private SocketService socketService;
    @Mock
    private UserService userService;
    @Mock
    private RoundService roundService;

    @InjectMocks
    GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void evaluateAnswers_ShouldUpdateStepsAndSendMessages() {
        //setup user, lobby and round
        Long lobbyId = 123L;
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setAnswerIndex(1L);
        user1.setTime(10L);
        users.add(user1);
        User user2 = new User();
        user2.setId(2L);
        user2.setAnswerIndex(2L);
        user2.setTime(8.0f);
        users.add(user2);
        User user3 = new User();
        user3.setId(3L);
        user3.setAnswerIndex(3L);
        user3.setTime(12f);
        users.add(user3);

        Lobby lobby = spy(new Lobby());
        Round round = new Round();
        round.setRightAnswer(2L);
        lobby.setRound(round);

        //setup return values for mocks
        doReturn(4L).when(lobby).getMaxSteps();
        when(lobbyService.getUsersFromLobby(lobbyId)).thenReturn(users);
        when(lobbyService.getLobby(lobbyId)).thenReturn(lobby);
        when(lobbyService.getPunishmentSteps(lobbyId)).thenReturn(5L);
        when(roundService.getRound(lobbyId)).thenReturn(round);

        //run the test
        gameService.evaluateAnswers(lobbyId);

        // Check that interactions with the mock have worked (updated stepstate and the send message)
        verify(userService, times(1)).updateStepStateOfUser(4L, 3L);
        verify(socketService, times(1)).sendMessageToRoom(lobbyId.toString(), "LOSER", "1");

    }
}