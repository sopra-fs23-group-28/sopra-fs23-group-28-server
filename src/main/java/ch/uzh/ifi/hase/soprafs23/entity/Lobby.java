package ch.uzh.ifi.hase.soprafs23.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "LOBBY")
public class Lobby{

    @Id
    private Long id;

    @Column(nullable = false)
    private Long creatorId;

    @Column
    private Long roundNumber;

    @Column
    private Long maxSteps;

    @Column
    @ElementCollection
    private List<Long> userIds;

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

    public void setRoundNumber(Long roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Long getMaxSteps() {
        return this.maxSteps;
    }

    public void setMaxSteps(Long maxSteps) {
        this.maxSteps = maxSteps;
    }

    public List<Long> getUserIds() {return new ArrayList<>(userIds);} //unmodifiableCollection is not okay, DTOMapper can't comprehend unmodifiableCollection
    public void addUserId(Long userId){this.userIds.add(userId);}
}
