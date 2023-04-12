package ch.uzh.ifi.hase.soprafs23.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class LobbyGetDTO {
    private Long id;
    private Long creatorId;
    private Long roundNumber;
    private Long maxSteps;
    private List<Long> userIds;

    public LobbyGetDTO() {
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

    public List<Long> getUserIds() {return new ArrayList<>(userIds);}

    public void addUserId(Long playerId){this.userIds.add(playerId);}
}
