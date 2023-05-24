package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.Difficulties;
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
    @Mock
    private UserService userService;

    @InjectMocks
    private LobbyService lobbyService;

    private Lobby testLobby;
    private User testUser;
    private Round testround;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testLobby = new Lobby();
        testLobby.setId(1111L);

        testUser = new User();
        testUser.setId(1L);

        testround = new Round();
        testLobby.setRound(testround);
        testround.setLobby(testLobby);


        // Mocking LobbyRepository
        Mockito.when(LobbyRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(testLobby));
        Mockito.when(userService.getUserById(Mockito.any())).thenReturn(testUser);
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
        lobbyService.joinLobby(testLobby.getId(), testUser);

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
        lobbyService.setMaxSteps(maxSteps, testLobby.getId());

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

    @Test
    void leaveLobby(){
        testUser.setUsername("testUser");
        testLobby.addUserId(testUser.getId());
        lobbyService.leaveLobby(testLobby.getId(), testUser);
        assertTrue(testLobby.getUserIds().size() == 0);
    }

    @Test
    void IsLobbyTimerOver(){
        testround.setTimerOver(false);
        assertTrue(!lobbyService.isLobbyTimerOver(testLobby.getId()));
    }

    @Test
    void setLobbyTimerOver(){
        testround.setTimerOver(false);
        lobbyService.setTimerOver(testLobby.getId(), true);
        assertTrue(testround.isTimerOver());
    }

    @Test
    void resetRound(){
        testround.setTimerOver(true);
        lobbyService.resetRound(testLobby.getId());
        assertTrue(!testround.isTimerOver());
    }

    @Test
    void setDifficulty(){
        lobbyService.setDifficulty(testLobby.getId(), Difficulties.EASY);
        assertEquals(testLobby.getDifficulty(), Difficulties.EASY);
    }

    @Test
    void incRoundNumber(){
        testLobby.resetRoundNumber();

        lobbyService.increaseRoundNumber(testLobby.getId());
        assertTrue(testLobby.getRoundNumber() == 1L);
    }

    @Test
    void resetAnswerCounter(){
        testround.incrementAnswerCount();
        assertTrue(testround.getAnswerCount() == 1L);

        lobbyService.resetAnswerCounter(testLobby.getId());
        assertTrue(testround.getAnswerCount() == 0L);
    }



}