package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
  public UserGetDTO setCamelColor(@PathVariable Long userId, @RequestBody String camelColor) {

      //update color
      User updatedUser = userService.setCamelColor(userId, camelColor);
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
    public void deleteUser(@PathVariable UserPostDTO userPostDTO) {

      User user = DTOMapper.INSTANCE.convertUserPostDTOtoUserEntity(userPostDTO);
      userService.deleteUser(user);
    }


}
