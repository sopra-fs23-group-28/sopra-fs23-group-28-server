package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.TriviaAPIHandler.APIOutput;
import ch.uzh.ifi.hase.soprafs23.TriviaAPIHandler.APIOutputQuestion;
import ch.uzh.ifi.hase.soprafs23.config.SocketService;
import ch.uzh.ifi.hase.soprafs23.constant.Categories;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    void incVoteCount() {
        roundservice.createRound(1111L);
        roundservice.incVoteCount(1111L);
        Round round = roundservice.getRound(1111L);
        assertEquals(round.getAnswerCount(), 1L);
    }

    /**
     * This test case validates the setAPIOutput() method of the RoundService class.
     * This method is responsible for the external Trivia API in our project and therefor crucial.
     * The test ensures that the current question is not null and the logic of fetching external questions work.
     */
    @Test
    void setAPIOutput() {

        // Create a new round with id 1111
        roundservice.createRound(1111L);

        // get the created round
        Round round = roundservice.getRound(1111L);

        // setup a few dummy answers and set them to the round
        List<String> answers = new ArrayList<String>();
        answers.add("dummyanswer");
        answers.add("dummyanswer");
        answers.add("dummyanswer");
        answers.add("dummyanswer");
        round.setAnswers(answers);

        // Create a dummy APIOutput object
        APIOutput dummyAPIOutput = new APIOutput();

        // Create a dummy APIOutputQuestion object and set its text
        APIOutputQuestion APIQuestion = new APIOutputQuestion();
        APIQuestion.setText("question");

        // Set the APIOutput object's question and answers
        dummyAPIOutput.setApiOutputQuestion(APIQuestion);
        dummyAPIOutput.setCorrectAnswer("answer");
        dummyAPIOutput.setIncorrectAnswers(answers);

        // Set the APIOutput to the round we created and assert it is not Null
        roundservice.setAPIOutput(1111L, dummyAPIOutput);
        assertNotNull(round.getCurrentQuestion());
    }
    
    @Test
    void addCategoryVote() {
    roundservice.createRound(1111L);
    Round round = roundservice.getRound(1111L);
    assertEquals(0,round.getCategoryVotes().size());
    roundservice.addCategoryVote(Categories.geography, 1111L);
    assertEquals(1,round.getCategoryVotes().size());

    }
}