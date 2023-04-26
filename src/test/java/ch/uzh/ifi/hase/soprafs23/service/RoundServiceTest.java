package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.config.SocketService;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoundServiceTest {

    @Mock
    private ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository LobbyRepository;
    @Mock
    private LobbyService lobbyService;

    @Mock
    private SocketService socketService;


    @InjectMocks
    private RoundService roundservice;

    private Lobby testLobby;
    private User testUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testLobby = new Lobby();
        testLobby.setId(1111L);

        testUser = new User();
        testUser.setId(1L);

        // Mocking LobbyRepository
        Mockito.when(LobbyRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(testLobby));
        Mockito.when(LobbyRepository.save(Mockito.any())).thenReturn(testLobby);
        Mockito.when(lobbyService.getLobby(Mockito.any())).thenReturn(testLobby);
        Mockito.doNothing().when(socketService).sendMessageToRoom(Mockito.any(),Mockito.any(),Mockito.any());

    }

    @Test
    void getRound() {

    }

    @Test
    void createRound() {
        roundservice.createRound(1111L);
        assertEquals(1111L, testLobby.getRound().getId());
    }


    @Test
    void chooseCategory() {
        roundservice.createRound(1111L);
        roundservice.chooseCategory(1111L);
        Round round = roundservice.getRound(1111L);
        assertTrue(round.getCategories().size() == 3);
        assertTrue(round.getCategories().contains(round.getChosenCategory()));
    }


 */
    @Test
    void incVoteCount() {
        roundservice.createRound(1111L);
        roundservice.incVoteCount(1111L);
        Round round = roundservice.getRound(1111L);
        assertEquals(round.getAnswerCount(), 1L);
    }

    @Test
    void setAPIOutput() {
        roundservice.createRound(1111L);

    }

    @Test
    void addCategoryVote() {
    }
}