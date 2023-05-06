package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
  private final LobbyService lobbyService;
  UserController(UserService userService, LobbyService lobbyService) {
      this.userService = userService;
      this.lobbyService = lobbyService;
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {

      //convert to internal representation
      User newUser = DTOMapper.INSTANCE.convertUserPostDTOtoUserEntity(userPostDTO);

      //create user
      User createdUser = userService.createUser(newUser);

      //return created User
      return DTOMapper.INSTANCE.convertUserEntityToUserGetDTO(createdUser);
  }

  @PutMapping("/users/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ResponseBody
  public UserGetDTO setCamelColor(@PathVariable Long userId, @RequestBody UserPostDTO userPostDTO) {
      //update color
      User updatedUser = userService.setCamelColor(userId, userPostDTO.getCamelColor());
      return DTOMapper.INSTANCE.convertUserEntityToUserGetDTO(updatedUser);

  }

  @GetMapping("/users/{lobbyId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<UserGetDTO> getAllUsers(@PathVariable Long lobbyId) {
      // fetch all users in the called lobby
      List<User> users = lobbyService.getUsersFromLobby(lobbyId);
      List<UserGetDTO> userGetDTOs = new ArrayList<>();

      // convert each user to the API representation
      for (User user : users) {
          userGetDTOs.add(DTOMapper.INSTANCE.convertUserEntityToUserGetDTO(user));
      }
      return userGetDTOs;
  }

    @DeleteMapping ("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestBody UserPostDTO userPostDTO) {
    User user = DTOMapper.INSTANCE.convertUserPostDTOtoUserEntity(userPostDTO);
    userService.deleteUser(user);
  }

    @PutMapping("/users/{userId}/states")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void resetStepState(@PathVariable Long userId, @RequestBody UserPutDTO userPutDTO) {
        User user = userService.getUserByToken(userPutDTO.getToken());
        if(!Objects.equals(user.getId(), userId)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not authenticated");
        userService.resetSteps(userId);
    }
}
