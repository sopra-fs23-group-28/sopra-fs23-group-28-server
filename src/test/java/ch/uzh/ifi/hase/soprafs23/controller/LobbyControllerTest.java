package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.config.SocketModule;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LobbyController.class)
class LobbyControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private UserService userService;
        @MockBean
        private LobbyService lobbyService;
        @MockBean
        private LobbyRepository lobbyRepository;
        @MockBean
        private SocketModule socketModule;

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

        //mock userService.getUserByToken, needed by LobbyService.createLobby
        given(userService.getUserByToken(Mockito.any())).willReturn(user);
        given(lobbyService.createLobby(user)).willReturn(lobby);
    }

    @Test
    void createLobbyAndGetPin() throws Exception {
        //send request and check if pin meets criteria
        mockMvc.perform(post("/lobbies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", allOf(greaterThanOrEqualTo(1000), lessThanOrEqualTo(9999))));
    }

    @Test
    void joinLobby() throws Exception {

        //send request and check if it throws errors
        mockMvc.perform(put("/lobbies/4400/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"123\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void lobbyInfo() throws Exception {
        //send request and check if it throws errors
        mockMvc.perform(put("/lobbies/4400/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"123\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void startGame() throws Exception {
        //send request and check if pin meets criteria
        mockMvc.perform(put("/lobbies/4400")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"123\"}"))
                .andExpect(status().isNoContent());
    }
}