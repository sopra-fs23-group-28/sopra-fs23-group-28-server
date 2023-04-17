package ch.uzh.ifi.hase.soprafs23.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Message {
    private MessageType type;
    private String message;
    private String room;

    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    @JsonCreator
    public Message(@JsonProperty("type") MessageType type, @JsonProperty("message") String message, @JsonProperty("room") String room) {
        this.type = type;
        this.message = message;
        this.room = room;
    }


    public String getMessage() {
        return this.message;
    }

    public String getRoom() {
        return this.room;
    }
}
