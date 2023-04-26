package ch.uzh.ifi.hase.soprafs23.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LOBBY")
public class Lobby{
    //1. mapping, 2. if Lobby gets saved, round gets saved too
    @OneToOne(mappedBy = "lobby", cascade = CascadeType.ALL)
    private Round round;

    @Id
    private Long id;

    @Column(nullable = false)
    private Long creatorId;

    @Column
    private Long roundNumber = 0L;

    @Column
    private Long maxSteps;


    @Column
    @ElementCollection
    private List<Long> userIds = new ArrayList<>();


    public Lobby() {
        this.userIds = new ArrayList<>();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}

    public Long getRoundNumber() {
        return this.roundNumber;
    }

    public void incRoundNumber() {
        this.roundNumber += 1;
    }
    public void resetRoundNumber(){this.roundNumber = 0L;}

    public Long getMaxSteps() {
        return this.maxSteps;
    }

    public void setMaxSteps(Long maxSteps) {
        this.maxSteps = maxSteps;
    }

    public List<Long> getUserIds() {return new ArrayList<>(userIds);} //unmodifiableCollection is not okay, DTOMapper can't comprehend unmodifiableCollection
    public void addUserId(Long userId){this.userIds.add(userId);}

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public void removeUserId(Long id){this.userIds.remove(id);}
}
