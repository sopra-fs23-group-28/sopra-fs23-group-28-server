package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.CamelColors;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Mockito.when(userRepository.findByToken(Mockito.any())).thenReturn(testUser);
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

        assertEquals(CamelColors.BLUE, UserWithCamel.getCamelColor());
    }

    @Test
    void updateStepState() {
        testUser.setStepState(4L);
        userService.updateStepStateOfUser(3L, testUser.getId());
        assertTrue(testUser.getStepState() == 7L);
    }

    @Test
    void updatedTimeAndAnswer() {
        testUser.setTime(0f);
        userService.updateTimeAndAnswer("1", 3.5f, 1L);
        assertTrue(testUser.getTime() == 3.5f);
    }

    @Test
    void resetSteps() {
        testUser.setStepState(5L);
        userService.resetSteps(testUser.getId());
        assertTrue(testUser.getStepState() == 0L);
    }

    @Test
    void setUserReady() {
        testUser.setIsReady(false);
        userService.setUserIsReady(testUser.getId());
        assertTrue(testUser.getIsReady() == true);
    }

}