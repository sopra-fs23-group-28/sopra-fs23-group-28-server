package ch.uzh.ifi.hase.soprafs23.config;

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
    public Message(MessageType type, String message, String room) {
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
