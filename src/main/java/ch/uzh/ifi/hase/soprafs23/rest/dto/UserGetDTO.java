package ch.uzh.ifi.hase.soprafs23.rest.dto;
import ch.uzh.ifi.hase.soprafs23.constant.CamelColors;

public class UserGetDTO {
  private Long id;
  private String username;
  private CamelColors camelColor;
  private String token;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getUsername() {return username;}
  public void setUsername(String username) {this.username = username;}
  public CamelColors getCamelColor() {return this.camelColor;}
  public void setCamelColor(CamelColors camelColor) {this.camelColor = camelColor;}
  public String getToken() {return token;}
  public void setToken(String token) {this.token = token;}
}
