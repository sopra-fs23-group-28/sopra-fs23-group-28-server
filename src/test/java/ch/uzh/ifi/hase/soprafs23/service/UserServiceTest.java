package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.CamelColors;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUsername");

        // Mocking userRepository
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(testUser));

    }
    @Test
    void createUser() {
        User createdUser = userService.createUser(testUser);

        assertEquals(createdUser.getId(), testUser.getId());
    }
    @Test
    void getUserById() {
        User foundUser = userService.getUserById(testUser.getId());

        assertEquals(foundUser.getId(), testUser.getId());
    }

    @Test
    void setCamelColor() {
        String color = "Blue";
        User UserWithCamel = userService.setCamelColor(testUser.getId(), color);

        assertEquals(UserWithCamel.getCamelColor(), CamelColors.BLUE);
    }


}