package ch.uzh.ifi.hase.soprafs23.rest.dto;
import ch.uzh.ifi.hase.soprafs23.constant.Difficulties;

public class DifficultyPutDTO {
    private String token;
    private Difficulties difficulty;
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
    public Difficulties getDifficulty() {return difficulty;}
    public void setDifficulty(Difficulties difficulty) {this.difficulty = difficulty;}
}
