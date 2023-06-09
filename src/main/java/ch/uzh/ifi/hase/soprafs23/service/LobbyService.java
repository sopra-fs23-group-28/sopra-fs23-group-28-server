package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.Difficulties;
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
    private final Random random = new Random();

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
    public void isUserTokenInLobby(String token, Long lobbyId) {
        Lobby lobby = getLobby(lobbyId);
        List<User> users = getUsersFromLobby(lobby.getId());

        for (User user2 : users) {
            if (user2.getToken().equals(token)) {
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void leaveLobby(Long lobbyId, User user) {
        Lobby lobby = getLobby(lobbyId);

        //check if user was in the round before deleting it
        List<User> users = getUsersFromLobby(lobby.getId());
        boolean userFound = false;
        for (User userToBeChecked : users) {
            if (user.getUsername().equals(userToBeChecked.getUsername())) {
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User was never in round!");
        }

        //remove the user
        lobby.removeUserId(user.getId());
        lobbyRepository.save(lobby);
    }

        //lets a user join a lobby
    public void joinLobby(Long lobbyId, User user) {
        Lobby lobby = getLobby(lobbyId);
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
    public void setMaxSteps(Long steps, Long lobbyId){
        Lobby lobby = getLobby(lobbyId);
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
    public void setTimerOver(Long lobbyId, boolean val){
        Round round = getLobby(lobbyId).getRound();
        round.setTimerOver(val);
        lobbyRepository.save(getLobby(lobbyId));
    }

    //authenticate that request is from creator
    public void validate(Lobby lobby, User user) {
        if (!lobby.getCreatorId().equals(user.getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Not authenticated!");
    }

    public boolean isLobbyReady(Long id){
        for(User u : getUsersFromLobby(id)){
            if (!u.getIsReady()) return false;
        }
        return true;
    }

    public void resetIsLobbyReady(Long id){
        for(User u : getUsersFromLobby(id)) {
            u.setIsReady(false);
        }
    }

    public void resetAnswerCounter(Long lobbyId) {
        Round round = getLobby(lobbyId).getRound();
        round.resetAnswerCount();
        lobbyRepository.save(getLobby(lobbyId));
    }

    public void increaseRoundNumber(Long lobbyId) {
        Lobby lobby = getLobby(lobbyId);
        lobby.incRoundNumber();
        lobbyRepository.save(lobby);
    }

    public void setDifficulty(Long lobbyId, Difficulties difficulty) {
        //fetch lobby
        Lobby lobby = getLobby(lobbyId);
        //set difficulty
        lobby.setDifficulty(difficulty);
        lobbyRepository.save(lobby);
    }

    public void resetRound(Long lobbyId){

        //Reset answer time to 15000 and answeridx to 0 for each user and at first initialization
        List<User> users = getUsersFromLobby(lobbyId);
        for(User u : users){
            u.setAnswerIndex(0L);
            u.setTime(15000);
        }

        setTimerOver(lobbyId, false);
        resetAnswerCounter(lobbyId);
        increaseRoundNumber(lobbyId);
    }


    public Long getPunishmentSteps(Long lobbyId){
        Lobby lobby = getLobby(lobbyId);
        return lobby.getPunishmentSteps();
    }

    public void setPunishmentSteps(Long lobbyId, Long punishmentSteps){
        Lobby lobby = getLobby(lobbyId);
        lobby.setPunishmentSteps(punishmentSteps);
        lobbyRepository.save(lobby);
    }
}
