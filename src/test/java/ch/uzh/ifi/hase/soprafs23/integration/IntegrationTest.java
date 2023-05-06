package ch.uzh.ifi.hase.soprafs23.integration;

import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTest {

    /**
     * This integration test checks whether the Game-Master is automatically
     * integrated into the lobby when a lobby is created.
     *
     * The test creates a user named "testUser", checks the response status, and saves
     * the token to use for creating the lobby.
     *
     * To create the lobby, the test uses the token from the POST response on line 47.
     *
     * The isUserTokenInLobby method is also implicitly tested.
     */

    // Constructor-based dependency injection is used to inject the LobbyService bean
    private final LobbyService lobbyService;

    @Autowired
    public IntegrationTest(LobbyService lobbyService){
        this.lobbyService = lobbyService;
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void integrationTestCreateLobby() throws Exception {
        // Create a user and obtain its token
        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        String token = jsonNode.get("token").asText();

        // Use the obtained token to create a lobby
        MvcResult resultCreation = mockMvc.perform(post("/lobbies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"" + token + "\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        // Parse the lobby ID from the POST response
        JsonNode jsonNode2 = objectMapper.readTree(resultCreation.getResponse().getContentAsString());
        Long lobbyId = Long.valueOf(jsonNode2.get("id").asText());

        // Assert that the user is in the lobby using the isUserTokenInLobby method
        assertDoesNotThrow(() -> lobbyService.isUserTokenInLobby(token, lobbyService.getLobby(lobbyId)));
    }

}
