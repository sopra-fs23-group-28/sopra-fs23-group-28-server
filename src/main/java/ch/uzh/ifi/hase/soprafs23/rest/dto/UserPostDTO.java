package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class UserPostDTO {
  private String username;

  private String token;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  public String getToken() {
        return token;
    }
  public void setToken(String token) {
        this.token = token;
    }
}
