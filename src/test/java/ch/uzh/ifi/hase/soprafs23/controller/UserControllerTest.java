package ch.uzh.ifi.hase.soprafs23.controller;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.doThrow;


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

        //mock userService.createUser
        given(userService.createUser(Mockito.any())).willReturn(user);

        // send request and except correct answer
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"uniqueUsername\"}"))
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
}