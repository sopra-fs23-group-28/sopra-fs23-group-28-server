package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.TriviaAPIHandler.APIOutput;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;


@Service
public class QuestionService {
    private final RestTemplate restTemplate;
    private final RoundService roundService;
    private final LobbyService lobbyService;

    public QuestionService(RestTemplate restTemplate, RoundService roundService, LobbyService lobbyService) {
        this.restTemplate = restTemplate;
        this.roundService = roundService;
        this.lobbyService = lobbyService;
    }

    public APIOutput getQuestion(String url) {
        ResponseEntity<List<APIOutput>> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<APIOutput>>() {});

        APIOutput responseBody = Objects.requireNonNull(response.getBody()).get(0);

        // Process the response body as needed
        return responseBody;
    }

    public void createQuestion(Long lobbyId){
        Lobby lobby = lobbyService.getLobby(lobbyId);
        Round round = roundService.getRound(lobbyId);

        //build the API Call String
        String TriviaAPICall = "https://the-trivia-api.com/v2/questions/?limit=1&difficulties=" + lobby.getDifficulty().toString().toLowerCase() + "&categories=" + round.getChosenCategory().toString();
        APIOutput question = getQuestion(TriviaAPICall);

        roundService.setAPIOutput(lobbyId, question);
    }
}