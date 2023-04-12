package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.CamelColors;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
*/
@Service
@Transactional
public class UserService {
  private final Logger log = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;
  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository) {
    this.userRepository = userRepository;
  }

    /**
                 UserService Methods
     */

  //creates user
  public User createUser(User newUser) {
    newUser.setToken(UUID.randomUUID().toString());
    checkIfUserExists(newUser);
    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();
    return newUser;
  }

  //sets the color of a camel
  public User setCamelColor(Long id, String color){

      //fetch user
      User user = getUserById(id);

      //update camelColor
      try {
          CamelColors camelColor = CamelColors.valueOf(color.toUpperCase());
          user.setCamelColor(camelColor);
      } catch(Exception e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid color");
      }

      return user;
  }

  //two methods for finding users
  public User getUserByToken(String token){
      return userRepository.findByToken(token);
  }
  public User getUserById(Long id){
      //fetch user
      Optional<User> OPUser = userRepository.findById(id);
      User user = OPUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
              "User not found"));
      return user;
    }

    public void deleteUser(User user){
      User userToDelete = userRepository.findByToken(user.getToken());
      if(userToDelete == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User to be deleted could not be found");
      userRepository.delete(userToDelete);
      userRepository.flush();
    }

  /**
        Helper Methods
  **/
  private void checkIfUserExists(User userToBeCreated) {
      User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());

      String baseErrorMessage = "The username provided is not unique. Therefore, the user could not be created!";
      if (userByUsername != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              baseErrorMessage);
  }
}
