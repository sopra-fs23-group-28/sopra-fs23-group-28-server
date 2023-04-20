package ch.uzh.ifi.hase.soprafs23.TriviaAPIHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class externalApiHandler {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
