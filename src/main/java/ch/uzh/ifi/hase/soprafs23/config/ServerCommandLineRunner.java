package ch.uzh.ifi.hase.soprafs23.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServerCommandLineRunner implements CommandLineRunner {
    private final SocketIOServer server;

    public ServerCommandLineRunner(SocketIOServer server1) {
        this.server = server1;
    }
    @Override
    public void run(String... args) throws Exception {server.start();}
}
