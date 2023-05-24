package ch.uzh.ifi.hase.soprafs23.config;

import ch.uzh.ifi.hase.soprafs23.constant.MessageType;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.service.*;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocketModuleTest {

    @Mock
    private SocketIOServer server;
    @Mock
    private SocketService socketService;
    @Mock
    private LobbyService lobbyService;
    @Mock
    private UserService userService;
    @Mock
    private RoundService roundService;
    @Mock
    private QuestionService questionService;
    @Mock
    private GameService gameService;
    @Mock
    private SocketIOClient client;
    @Mock
    private HandshakeData handshakeData;
    private SocketModule socketModule;

    @BeforeEach
    public void setUp() {
        socketModule = new SocketModule(server, socketService, lobbyService, userService, roundService, questionService, gameService);
    }

    @Test
    public void testOnConnected() {

        //setup lobby
        Lobby lobby = new Lobby();
        lobby.setId(1234L);
        when(lobbyService.getLobby(Mockito.any())).thenReturn(lobby);

        String room = "1234";
        String token = "token123";
        String isSpectator = null;

        //setup return values
        when(client.getHandshakeData()).thenReturn(handshakeData);
        when(handshakeData.getSingleUrlParam("room")).thenReturn(room);
        when(handshakeData.getSingleUrlParam("token")).thenReturn(token);
        when(handshakeData.getSingleUrlParam("spectator")).thenReturn(isSpectator);

        //call the connect listener
        ConnectListener listener = socketModule.onConnected();
        listener.onConnect(client);

        //check whether methods on service mocks were called
        verify(lobbyService).isUserTokenInLobby(eq(token), eq(Long.parseLong(room)));
        verify(socketService).sendMessageToRoom(eq(room), eq("NEWUSER"), eq("ANEWUSERJOINED"));
        verify(client).joinRoom(room);
        verify(socketService).sendMessage(eq("JOIN"), eq(client), eq("JOINEDROOM"));
    }

    @Test
    public void testOnReadyReceived() throws Exception {
       Message message = new Message(MessageType.CLIENT, " ", "123");

        //setup lobby and user
        Lobby lobby = new Lobby();
        Long lobbyId = 123L;
        lobby.setId(lobbyId);

        User user = new User();
        user.setId(1L);
        String token = "token123";

        //setup return values
        when(client.getHandshakeData()).thenReturn(handshakeData);
        when(client.getHandshakeData().getSingleUrlParam("token")).thenReturn(token);
        when(lobbyService.getLobby(lobbyId)).thenReturn(lobby);
        when(userService.getUserByToken(token)).thenReturn(user);
        when(lobbyService.isLobbyReady(lobbyId)).thenReturn(true);

        //call the listener
        socketModule.onReadyReceived().onData(client, message, Mockito.any());

        //check whether methods on service mocks were called
        verify(userService).setUserIsReady(user.getId());
        verify(roundService).createRound(lobbyId);
        verify(lobbyService).resetIsLobbyReady(lobbyId);
    }
}