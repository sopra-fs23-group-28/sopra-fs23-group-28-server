package ch.uzh.ifi.hase.soprafs23.config;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
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
    private final UserService userService;

    public SocketModule(SocketIOServer server, SocketService socketService, LobbyService lobbyService, UserService userService) {
        this.lobbyService = lobbyService;
        this.server = server;
        this.socketService = socketService;
        this.userService = userService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());
        server.addEventListener("GAMESTART", Message.class, onGamestartReceived());
        server.addEventListener("READY", Message.class, onReadyReceived());
    }

    private DataListener<Message> onGamestartReceived(){
        return (senderClient, data, ackSender) -> {
            //if Lobby was not initiated for the start yet, send back an error message. We check if maxSteps was set or not
            if (lobbyService.getLobby(Long.valueOf(data.getRoom())).getMaxSteps() == null) {
                socketService.sendMessage("GAMESTART", senderClient, "NOSTART");
            }
            else {
                //if successful, send message to room that the Game can be started
                socketService.sendMessageToRoom(data.getRoom(), "GAMESTART", senderClient, "GAMESTART");
            }
        };
    }
    private DataListener<Message> onReadyReceived(){
        return (senderClient, data, ackSender) -> {
            //we check if each user in the lobby is ready (has sent something to the READY listener).
            // if yes, send out GETCATEGORY
            if (lobbyService.isLobbyReady(Long.valueOf(data.getRoom()))) {
                lobbyService.createRound(Long.valueOf(data.getRoom()));
                socketService.sendMessageToRoom(data.getRoom(), "READY", senderClient, "GETCATEGORY");
            }
            else {
                //if not, send out NOTREADYYET
                socketService.sendMessageToRoom(data.getRoom(), "READY", senderClient, "NOTREADYYET");
            }
        };
    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.sendMessageToRoom(data.getRoom(),"get_message", senderClient, data.getMessage());
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
                    socketService.sendMessageToRoom(room,"NEWUSER",client,"ANEWUSERJOINED");

                    //join the socketIO room
                    client.joinRoom(room);

                    socketService.sendMessage("JOIN", client, "JOINEDROOM");


                    //check if lobby is full, if yes, send Message to room that round can be started
                    if (lobbyService.getUsersFromLobby(lobby.getId()).size() == 4) {
                        socketService.sendMessageToRoom(room,"GAMEMASTER",client,"LOBBYISFULL");
                    }

                }
                catch(ResponseStatusException e) {
                    System.out.println(e.getStackTrace());
                    e.printStackTrace();
                    socketService.sendMessage("JOIN", client, "AUTHFAIL");
                    client.disconnect();
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
