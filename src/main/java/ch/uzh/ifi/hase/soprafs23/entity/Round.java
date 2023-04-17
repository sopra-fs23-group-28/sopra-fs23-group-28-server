package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.Categories;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "ROUND")
public class Round{

    @OneToOne
    @JoinColumn(name = "id")
    private Lobby lobby;

    @Id
    private Long id;


    @Column
    @ElementCollection
    private List<Categories> categories = new ArrayList<>();

    @Column
    @ElementCollection
    private List<Categories> categoryVotes = new ArrayList<>();

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
        return this.categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public Categories getChosenCategory() {
        return chosenCategory;
    }

    public void setChosenCategory(Categories chosenCategory) {
        this.chosenCategory = chosenCategory;
    }

    public List<String> getCurrentQuestions() {
        return this.currentQuestions;
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

    public List<Categories> getCategoryVotes() {
        return this.categoryVotes;
    }

    public void addCategoryVotes(Categories category) {
        this.categoryVotes.add(category);
    }

    public void resetCategoryVotes(Categories category) {
        this.categoryVotes = new ArrayList<Categories>();
    }

}
