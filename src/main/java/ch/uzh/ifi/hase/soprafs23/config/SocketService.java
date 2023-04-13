package ch.uzh.ifi.hase.soprafs23.config;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SocketService {
    private static final Logger log = LoggerFactory.getLogger(SocketModule.class);

    public void sendMessage(String eventName, SocketIOClient client, String message) {
        client.sendEvent(eventName, new Message(MessageType.SERVER, message));
    }

    public void sendMessageToRoom(String room, String eventName, SocketIOClient senderClient, String message) {

        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) { //go through all clients
            log.info(String.valueOf(client.getNamespace()));
            log.info(client.getNamespace().getRoomOperations(room).toString());
            log.info(senderClient.getNamespace().getRoomOperations(room).getClients().toString());

                client.sendEvent(eventName,
                        new Message(MessageType.SERVER, message));

        }
    }
}
