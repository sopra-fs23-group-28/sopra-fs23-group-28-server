package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.Categories;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "ROUND")
public class Round{

    @OneToOne(mappedBy = "Lobby")
    private Lobby lobby;

    @Id
    private Long id;
    @Column
    @ElementCollection
    private List<Categories> categories = new ArrayList<>();

    @Column
    private Categories chosenCategory;

    @Column
    @ElementCollection
    private List<String> currentQuestions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Categories> getCategories() {
        return new ArrayList<>(categories);
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public Categories getChosenCategory() {
        return chosenCategory;
    }

    public void setChosenCategory(Categories choosenCategory) {
        this.chosenCategory = choosenCategory;
    }

    public List<String> getCurrentQuestions() {
        return new ArrayList<>(currentQuestions);
    }

    public void setCurrentQuestions(List<String> currentQuestions) {
        this.currentQuestions = currentQuestions;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
