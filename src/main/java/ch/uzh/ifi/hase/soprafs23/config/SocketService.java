package ch.uzh.ifi.hase.soprafs23.config;

import ch.uzh.ifi.hase.soprafs23.constant.MessageType;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocketService {
    private static final Logger log = LoggerFactory.getLogger(SocketModule.class);
    private final SocketIOServer socketIOServer;

    @Autowired
    public SocketService(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }
    public void sendMessage(String eventName, SocketIOClient client, String message) {
        client.sendEvent(eventName, new Message(MessageType.SERVER, message));
    }

    public void sendMessageToRoom(String room, String eventName, String message) {
        for (SocketIOClient client : socketIOServer.getRoomOperations(room).getClients()) { //go through all clients
            client.sendEvent(eventName, new Message(MessageType.SERVER, message));
        }
    }
}
