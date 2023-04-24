package ch.uzh.ifi.hase.soprafs23.config;

import com.corundumstudio.socketio.SocketIOServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

    @Value("${socketio.host:#{systemEnvironment['SOCKETIO_HOST']}}")
    private String host;
    @Value("${socketio.port:#{systemEnvironment['SOCKETIO_PORT']}}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        return new SocketIOServer(config);
    }
}