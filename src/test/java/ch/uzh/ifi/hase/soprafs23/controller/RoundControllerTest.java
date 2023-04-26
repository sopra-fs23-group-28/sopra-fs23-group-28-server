package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.QuestionService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
    private RoundController roundController;


    //TODO: BEFOREEACH
/*
    @Test
    void createLobbyAndGetPin() throws Exception {

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

        //mock userService.getUserByToken, needed by LobbyService.createLobby
        given(userService.getUserByToken(Mockito.any())).willReturn(user);
        given(lobbyService.createLobby(user)).willReturn(lobby);

        //send request and check if pin meets criteria
        mockMvc.perform(post("/lobbies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", allOf(greaterThanOrEqualTo(1000), lessThanOrEqualTo(9999))));
    }

 */

    @Test
    void getRoundInfo() throws Exception {
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


        given(lobbyService.createLobby(user)).willReturn(lobby);

        mockMvc.perform(get("/lobbies/4400/rounds"))
                .andExpect(status().isOk());

    }

    @Test
    void receiveCategoryAnswers() throws Exception {


        //setup lobby & set lobbycreator for user
        Lobby lobby = new Lobby();
        lobby.setId(4400L);
        lobby.addUserId(1L);
        lobby.setCreatorId(1L);

        Mockito.doNothing().when(lobbyService).isUserTokenInLobby(Mockito.any(), Mockito.any());
        given(lobbyService.getLobby(lobby.getId())).willReturn(lobby);
        //given(lobbyService.isUserTokenInLobby(Mockito.any(), Mockito.any()));


        mockMvc.perform(put("/lobbies/4400/categories/1"))
                .andExpect(status().isNoContent());



    }



    @Test
    void receiveQuestionAnswers() {
    }


}