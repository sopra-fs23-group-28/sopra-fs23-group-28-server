package ch.uzh.ifi.hase.soprafs23.TriviaAPIHandler;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APIOutputQuestion {

    @JsonProperty("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}