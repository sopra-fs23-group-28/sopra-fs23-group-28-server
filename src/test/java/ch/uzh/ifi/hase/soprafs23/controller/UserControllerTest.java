package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LobbyService lobbyService;

    @MockBean
    private UserService userService;

    @Test
    void createUser_validInput() throws Exception {
        // setup user
        User user = new User();
        user.setId(1L);
        user.setUsername("uniqueUsername");
        user.setToken("123");

        ObjectMapper objectMapper = new ObjectMapper();

        //mock userService.createUser
        given(userService.createUser(Mockito.any())).willReturn(user);

        // send request and except correct answer
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.token", is(user.getToken())));

    }

    @Test
    void createUser_usernameAlreadyTaken() throws Exception {
        //mock userService.createUser
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "The username provided is not unique. Therefore, the user could not be created!"))
                .when(userService).createUser(Mockito.any());

        // perform POST request and expect BAD_REQUEST
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"uniqueUsername\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("The username provided is not unique. Therefore, the user could not be created!"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Create mock data
        List<User> users = new ArrayList<>();
        User testUser1 = new User();
        testUser1.setUsername("Testuser1");
        users.add(testUser1);

        // mock getUsersFromLobby
        when(lobbyService.getUsersFromLobby(Mockito.any())).thenReturn(users);

        // Send request and expect correct answer
        mockMvc.perform(get("/users/{lobbyId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username", is("Testuser1")));

        // Verify lobbyService.getUsersFromLobby is called
        verify(lobbyService, times(1)).getUsersFromLobby(1L);
    }

    @Test
    public void testDeleteUser() throws Exception {

        // Create mock data
        User user = new User();
        user.setUsername("testuser");

        Mockito.doNothing().when(userService).deleteUser(Mockito.any());

        // Send request and expect correct answer
        mockMvc.perform(delete("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"123\"}"))
                .andExpect(status().isNoContent());
    }


}