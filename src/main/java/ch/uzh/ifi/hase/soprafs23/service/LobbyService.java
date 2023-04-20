package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class LobbyService {
    private final UserService userService;
    private final LobbyRepository lobbyRepository;


    @Autowired
    public LobbyService(UserService userService, @Qualifier("lobbyRepository") LobbyRepository lobbyRepository) {
        this.userService = userService;
        this.lobbyRepository = lobbyRepository;
    }

    /**
     * LobbyService Methods
     */

    //create a lobby
    public Lobby createLobby(User user) {
        Lobby lobby = new Lobby();

        //generate Id
        Random random = new Random();
        int pin = random.nextInt(9000) + 1000;
        lobby.setId((long) pin);

        //set Creator
        lobby.setCreatorId(user.getId());
        user.setGameCreator(true);
        lobby.addUserId(user.getId());

        //save lobby
        lobby = lobbyRepository.save(lobby);

        return lobby;
    }

    //fetches lobby from repository
    public Lobby getLobby(Long id) {
        Optional<Lobby> OPlobby = lobbyRepository.findById(id);
        Lobby lobby = OPlobby.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Lobby not found"));
        return lobby;
    }

    //checks if given token of a user exists in a given lobby
    public void isUserTokenInLobby(String token, Lobby lobby) {
        List<User> users = getUsersFromLobby(lobby.getId());

        for (User user2 : users) {
            if (user2.getToken().equals(token)) {
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    //lets a user join a lobby
    public void joinLobby(Lobby lobby, User user) {
        List<User> users = getUsersFromLobby(lobby.getId());
        if(users.size() >= 4) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Lobby is full!");

        //check if username already taken
        for (User userToBeChecked : users) {
            if (user.getUsername().equals(userToBeChecked.getUsername()))
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Name already taken!");
        }
        lobby.addUserId(user.getId());
        lobbyRepository.save(lobby);
    }

    //get all users from a lobby
    public List<User> getUsersFromLobby(Long id){
        //fetch lobby
        Lobby lobby = getLobby(id);

        //get all users and save them in a list
        List<Long> ids = lobby.getUserIds();
        List<User> users = new ArrayList<>();
        for(Long Id : ids){
            users.add(userService.getUserById(Id));
        }
        return users;
    }

    //sets the maximum steps for a game
    public void setMaxSteps(Long steps, Lobby lobby){
        lobby.setMaxSteps(steps);
        lobbyRepository.save(lobby);
    }

    public boolean isLobbyTimerOver(Long lobbyId){
        Lobby lobby = getLobby(lobbyId);
        Round round = lobby.getRound();

        if(!round.isTimerOver()) {
            round.setTimerOver(true);
            lobbyRepository.save(getLobby(lobbyId));
            return false;
        }
        else return true;
    }

    //authenticate that request is from creator
    public void validate(Lobby lobby, User user) {
        if (!lobby.getCreatorId().equals(user.getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Not authenticated!");
    }

    public boolean isLobbyReady(Long id){
        for(User u : getUsersFromLobby(id)){
            System.out.println("User "+ u.getUsername() + " is " + u.getIsReady());
            if (!u.getIsReady()) return false;
        }
        return true;
    }
}
