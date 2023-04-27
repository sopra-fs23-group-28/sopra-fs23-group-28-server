package ch.uzh.ifi.hase.soprafs23.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SocketIOConfig {


    private String host = "0.0.0.0";

    private Integer port = 65080;

    /**
     * @return
     */
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setOrigin("http://sopra-fs23-group-28-client.oa.r.appspot.com");
        return new SocketIOServer(config);
    }
}