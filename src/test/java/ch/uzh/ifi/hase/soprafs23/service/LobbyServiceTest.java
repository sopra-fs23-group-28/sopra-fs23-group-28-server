package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LobbyServiceTest {

    @Mock
    private LobbyRepository LobbyRepository;


    @InjectMocks
    private LobbyService lobbyService;

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

    }

    @Test
    void createLobby() {

        //create test lobby
        Lobby createdLobby = lobbyService.createLobby(testUser);

        //assertions
        assertTrue(createdLobby.getId() instanceof Long);
        String id = createdLobby.getId().toString();
        assertEquals(4, id.length());
    }

    @Test
    void getLobby() {
        Lobby foundLobby = lobbyService.getLobby(1111L);
        assertEquals(testLobby.getId(), foundLobby.getId());
    }

    @Test
    void joinLobby() {
        lobbyService.joinLobby(testLobby, testUser);

        //check that userId is in Lobby
        List<Long> ids = testLobby.getUserIds();
        assertTrue(ids.contains(testUser.getId()));
    }

    @Test
    void getUsersFromLobby() {
        List<User> users = lobbyService.getUsersFromLobby(1111L);

        //check that list is empty
        assertTrue(users.isEmpty());

    }

    @Test
    void setMaxSteps() {
        Long maxSteps = 3L;
        lobbyService.setMaxSteps(maxSteps, testLobby);

        assertEquals(testLobby.getMaxSteps(), maxSteps);
    }

    @Test
    void validate() {
        // create a new user
        User tuser = new User();
        tuser.setId(2L);

        // try to validate user with non-matching creatorId
        assertThrows(ResponseStatusException.class, () -> {
            Lobby lobby = new Lobby();
            lobby.setCreatorId(1L);
            lobbyService.validate(lobby, tuser);
        }, "Not authenticated!");
    }
/*
    @Test
    void isLobbyTimerOver() {
        Lobby lobby = new Lobby();
        Long id= lobby.getId();

        Round round = new Round();
        lobby.setRound(round);
        round.setTimerOver(false);

        assertFalse(lobbyService.isLobbyTimerOver(id));

    }


 */
    @Test
    void setTimerOver() {

    }



    @Test
    void isLobbyReady() {
    }

    @Test
    void resetAnswerCounter() {
    }

    @Test
    void increaseRoundNumber() {
    }
}