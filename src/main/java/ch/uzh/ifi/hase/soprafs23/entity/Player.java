package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.BonusTools;

import javax.persistence.*;

@Entity
@Table(name = "PLAYER")
public class Player {
    @Id
    private Long playerId;

    @Column(nullable = false)
    private boolean gameCreator;

    @Column(nullable = false)
    private Long stepState;

    @Column()
    private BonusTools bonusTool;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lobby_id")
    private Lobby lobby;

    public Long getPlayerId() {return playerId;}
    public void setPlayerId(Long playerId) {this.playerId = playerId;}

    public Long getplayerId() {
        return playerId;
    }

    public void setplayerId(Long playerId) {
        this.playerId = playerId;
    }

    public BonusTools getBonusTools() {return bonusTool;}
    public void setBonusTools(BonusTools bonusTool) {this.bonusTool = bonusTool;}
    public Long getStepState() {return this.stepState;}

    public void setStepState(Long stepState) {this.stepState = stepState;}
}
