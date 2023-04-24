package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.BonusTools;
import ch.uzh.ifi.hase.soprafs23.constant.CamelColors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String token;

  @Column()
  private CamelColors camelColor;

  @Column()
  private Long stepState = 0L;

  @Column()
  private BonusTools bonusTool;

  @Column()
  private boolean gameCreator;

  @Column()
  private boolean isReady;

  @Column()
  private float time;

  @Column()
  private Long answerIndex;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {this.id = id;}
  public CamelColors getCamelColor() {
    return this.camelColor;
  }
  public void setCamelColor(CamelColors camelColor) {this.camelColor = camelColor;}
  public void setUsername(String username) {
    this.username = username;
  }
  public String getUsername() {return this.username;}
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }
  public BonusTools getBonusTools() {return bonusTool;}
  public void setBonusTools(BonusTools bonusTool) {this.bonusTool = bonusTool;}
  public Long getStepState() {return this.stepState;}
  public void setStepState(Long stepState) {this.stepState = stepState;}
  public void setGameCreator(boolean gameCreator){this.gameCreator = gameCreator;}
  public boolean getGameCreator(){return this.gameCreator;}
  public boolean getIsReady(){return this.isReady;}
  public void setIsReady(boolean isReady){this.isReady = isReady;}
  public void setAnswerIndex(Long answerIndex){this.answerIndex = answerIndex;}
  public Long getAnswerIndex(){return answerIndex;}
  public float getTime() {return time;}
  public void setTime(float time) {this.time = time;}
  public void updateStepState(Long stepUpdate){this.stepState += stepUpdate;}
}
