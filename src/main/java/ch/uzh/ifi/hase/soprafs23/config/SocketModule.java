package ch.uzh.ifi.hase.soprafs23.config;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.service.*;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static java.lang.Long.parseLong;

@Component
public class SocketModule {

    private final SocketIOServer server;
    private final SocketService socketService;
    private static final Logger log = LoggerFactory.getLogger(SocketModule.class);
    private final LobbyService lobbyService;
    private final RoundService roundService;
    private final UserService userService;
    private final QuestionService questionService;
    private final GameService gameService;

    public SocketModule(SocketIOServer server, SocketService socketService, LobbyService lobbyService, UserService userService, RoundService roundService, QuestionService questionService, GameService gameService) {
        this.lobbyService = lobbyService;
        this.questionService = questionService;
        this.server = server;
        this.roundService = roundService;
        this.socketService = socketService;
        this.userService = userService;
        this.gameService = gameService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("GAMESTART", Message.class, onGamestartReceived());
        server.addEventListener("READY", Message.class, onReadyReceived());
        server.addEventListener("TIMERSTOPCATEGORY", Message.class, onTimerStopCategoryReceived());
        server.addEventListener("TIMERSTOPQUESTION", Message.class, onTimerStopQuestionReceived());
        server.addEventListener("ROUND", Message.class, onRoundReceived());
    }

    private DataListener<Message> onRoundReceived(){
        return (client, data, ackSender) -> {
            Long lobbyId = Long.valueOf(data.getRoom());
            String token = client.getHandshakeData().getSingleUrlParam("token");
            try {
                lobbyService.isUserTokenInLobby(token, lobbyService.getLobby(lobbyId));

            } catch (Exception e) {
                socketService.sendMessage("ROUND", client, "AUTHFAIL");
                return;
            }
            if(!userService.getUserByToken(token).getGameCreator()) {
                socketService.sendMessage("ROUND", client, "NOT GAME CREATOR");
                return;
            }

            //set the timer to false because at this point evaluating answer has finished completely
            lobbyService.setTimerOver(lobbyId, false);
            lobbyService.resetAnswerCounter(lobbyId);
            lobbyService.increaseRoundNumber(lobbyId);
            questionService.createQuestion(lobbyId);
        };
    };


    private DataListener<Message> onTimerStopQuestionReceived(){
        return (client, data, ackSender) -> {
            Long lobbyId = Long.valueOf(data.getRoom());

            if(lobbyService.isLobbyTimerOver(lobbyId)) {
                socketService.sendMessageToRoom(data.getRoom(), "ROUND", "TIMERALREADYSTOPPED");
            }
            else {
                //execute evaluateAnswers as soon as timer is over
                socketService.sendMessageToRoom(data.getRoom(), "ROUND", "VOTINGDONE");
                gameService.evaluateAnswers(lobbyId);


            }
        };
    }
    private DataListener<Message> onTimerStopCategoryReceived(){
        return (client, data, ackSender) -> {

            if(lobbyService.isLobbyTimerOver(Long.valueOf(data.getRoom()))) {
                socketService.sendMessageToRoom(data.getRoom(), "CATEGORY", "TIMERALREADYSTOPPED");
            }
            else {
                //execute chooseCategory as soon as timer is over
                roundService.chooseCategory(Long.valueOf(data.getRoom()));
                socketService.sendMessageToRoom(data.getRoom(), "CATEGORY", "VOTINGDONE");
            }
        };
    }

    private DataListener<Message> onGamestartReceived(){
        return (senderClient, data, ackSender) -> {
            //if Lobby was not initiated for the start yet, send back an error message. We check if maxSteps was set or not
            if (lobbyService.getLobby(Long.valueOf(data.getRoom())).getMaxSteps() == null) {
                socketService.sendMessage("GAMESTART", senderClient, "NOSTART");
            }
            else {
                //if successful, send message to room that the Game can be started
                socketService.sendMessageToRoom(data.getRoom(), "GAMESTART", "GAMESTART");
            }
        };
    }
    private DataListener<Message> onReadyReceived(){
        return (senderClient, data, ackSender) -> {
            //we check if each user in the lobby is ready (has sent something to the READY listener).
            // if yes, send out GETCATEGORY

            Long lobbyId = Long.valueOf(data.getRoom());
            String token = senderClient.getHandshakeData().getSingleUrlParam("token");
            User user = userService.getUserByToken(token);
            userService.userIsReady(user.getId());

            //if lobby is ready, create the Round and send a message to all clients that they can fetch the category
            if (lobbyService.isLobbyReady(lobbyId)) {
                roundService.createRound(lobbyId);
                socketService.sendMessageToRoom(data.getRoom(), "READY", "GETCATEGORY");
            }
            else {
                //if not, send out NOTREADYYET
                socketService.sendMessageToRoom(data.getRoom(), "READY", "NOTREADYYET");
            }
        };
    }


    private ConnectListener onConnected() {
        return (client) -> {
            //fetch room from URL parameter
            String room = client.getHandshakeData().getSingleUrlParam("room");
            String token = client.getHandshakeData().getSingleUrlParam("token");

                //try to get the Lobby with this specific room PIN - if it does not exist, an error is being raised and caught.
                try {
                    log.info("Socket ID[{}]  Client tries to connect...", client.getSessionId().toString());

                    //does this lobby exist?
                    Lobby lobby = lobbyService.getLobby(parseLong(room));

                    //is this user approved to join?

                    lobbyService.isUserTokenInLobby(token, lobby);

                    //send message to whole room about a new user having joined
                    socketService.sendMessageToRoom(room,"NEWUSER","ANEWUSERJOINED");

                    //join the socketIO room
                    client.joinRoom(room);

                    socketService.sendMessage("JOIN", client, "JOINEDROOM");


                    //check if lobby is full, if yes, send Message to room that round can be started
                    if (lobbyService.getUsersFromLobby(lobby.getId()).size() == 4) {
                        socketService.sendMessageToRoom(room,"GAMEMASTER","LOBBYISFULL");
                    }

                }
                catch(ResponseStatusException e) {
                    e.printStackTrace();
                    socketService.sendMessage("JOIN", client, "AUTHFAIL");
                    //client.disconnect();
                    log.info("disconnected bc of wrong PIN or bc user is not in lobby");
                }

        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }



}
