package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.CamelColors;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {
    private final UserService userService;
    UserController(UserService userService) {this.userService = userService;}

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser() {
        User mockUser = new User();
        mockUser.setUsername("Bob");
        mockUser.setToken("token");
        return DTOMapper.INSTANCE.convertUserEntityToUserGetDTO(mockUser);
    }

    @PutMapping("/users/{userId}/camelcolor") //TODO: @Pathvariable reinnehmen
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public UserGetDTO setCamelColor() {
        User mockUser = new User();
        mockUser.setUsername("Bob");
        mockUser.setToken("token");
        mockUser.setCamelColor(CamelColors.RED);
        return DTOMapper.INSTANCE.convertUserEntityToUserGetDTO(mockUser);
    }
}
