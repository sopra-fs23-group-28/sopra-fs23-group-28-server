package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "LOBBY")
public class Lobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long roundNumber;

    @Column(nullable = false, unique = true)
    private Long maxSteps;

    @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Long roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Long getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(Long maxSteps) {
        this.maxSteps = maxSteps;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
