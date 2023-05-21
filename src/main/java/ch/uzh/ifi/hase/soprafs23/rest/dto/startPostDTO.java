package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class startPostDTO {
    private Long maxSteps;
    private String token;
    public Long getMaxSteps() {
        return maxSteps;
    }
    public void setMaxSteps(Long maxSteps) {
        this.maxSteps = maxSteps;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
