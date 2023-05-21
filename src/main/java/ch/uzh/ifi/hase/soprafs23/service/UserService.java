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

  public void updateStepStateOfUser(Long val, Long id){
      User user = getUserById(id);
      user.updateStepState(val);
      userRepository.save(user);
  }

  //sets the color of a camel
  public User setCamelColor(Long id, String color){

      //fetch user
      User user = getUserById(id);

      //update camelColor
      try {
          System.out.println(color);
          System.out.println(id);
          System.out.println(user.getUsername());
          System.out.println(user.getId());
          CamelColors camelColor = CamelColors.valueOf(color.toUpperCase());
          user.setCamelColor(camelColor);
          System.out.println(user.getUsername());
      } catch(Exception e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid color");
      }

      return user;
  }

  //two methods for finding users
  public User getUserByToken(String token){
      User user = userRepository.findByToken(token);
      if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
              "User not found");
      else return user;
  }

  public User getUserById(Long id){
      //fetch user
      Optional<User> OPUser = userRepository.findById(id);
      User user = OPUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
              "User not found"));
      return user;
    }

    public void deleteUser(String usertoken){
      User userToDelete = userRepository.findByToken(usertoken);
      if(userToDelete == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User to be deleted could not be found");
      userRepository.delete(userToDelete);
      userRepository.flush();
    }

    public void updateTimeAndAnswer(String token, float time, Long answerIdx){
      User user = getUserByToken(token);
      user.setTime(time);
      user.setAnswerIndex(answerIdx);

      userRepository.save(user);
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

    public void setUserIsReady(Long id) {
      User user = getUserById(id);
      user.setIsReady(true);
      userRepository.save(user);

    }

    public void resetSteps(Long id){
      User user = getUserById(id);
      user.setStepState(0L);
      user.setIsReady(false);
      userRepository.save(user);
    }


}
