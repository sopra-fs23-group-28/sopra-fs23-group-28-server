package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class DifficultyPutDTO {
    private String token;
    private Long difficultyWheelDegree;
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
    public Long getDifficultyWheelDegree() {return difficultyWheelDegree;}
    public void setDifficultyWheelDegree(Long difficultyWheelDegree) {this.difficultyWheelDegree = difficultyWheelDegree;}
}
