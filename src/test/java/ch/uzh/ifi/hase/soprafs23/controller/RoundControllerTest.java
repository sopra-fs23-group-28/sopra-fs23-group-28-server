package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.Categories;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RoundController.class)
class RoundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LobbyService lobbyService;

    @MockBean
    private UserService userService;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private GameService gameService;



    @MockBean
    private RoundService roundService;


    @BeforeEach
    void setUp() {
        // setup user
        User user = new User();
        user.setId(1L);
        user.setUsername("uniqueUsername");
        user.setToken("123");

        //setup lobby & set lobbycreator for user
        Lobby lobby = new Lobby();
        lobby.setId(4400L);
        lobby.addUserId(1L);
        lobby.setCreatorId(1L);
        user.setGameCreator(true);

        Mockito.doNothing().when(lobbyService).isUserTokenInLobby(Mockito.any(), Mockito.any());
        given(lobbyService.getLobby(lobby.getId())).willReturn(lobby);

        //setup round
        Round round = new Round();
        lobby.setRound(round);
        round.setLobby(lobby);
        round.resetAnswerCount();
        ArrayList<Categories> list = new ArrayList<>();
        list.add(Categories.science);
        round.setCategories(list);


        Mockito.doNothing().when(lobbyService).isUserTokenInLobby(Mockito.any(), Mockito.any());
        Mockito.doNothing().when(userService).updateTimeAndAnswer(Mockito.any(), Mockito.anyFloat(),Mockito.anyLong());
        Mockito.doNothing().when(roundService).incVoteCount(Mockito.any());
        Mockito.doNothing().when(gameService).evaluateAnswers(Mockito.any());
        given(roundService.getRound(lobby.getId())).willReturn(round);
        //mock userService.getUserByToken, needed by LobbyService.createLobby
        given(userService.getUserByToken(Mockito.any())).willReturn(user);
        given(lobbyService.createLobby(user)).willReturn(lobby);
    }


    @Test
    void getRoundInfo() throws Exception {


        mockMvc.perform(get("/lobbies/4400/rounds"))
                .andExpect(status().isOk());

    }

    @Test
    void receiveCategoryAnswers() throws Exception {


        mockMvc.perform(put("/lobbies/4400/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"token\": \"123\"}"))
                .andExpect(status().isNoContent());
    }



    @Test
    void receiveQuestionAnswers() throws Exception {
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("token", "123");
        request.setParameter("time", "10");
        request.setParameter("answerIndex", "2");


        mockMvc.perform(put("/lobbies/4400/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answerIndex\": \"1\"}"))
                .andExpect(status().isNoContent());
    }


}