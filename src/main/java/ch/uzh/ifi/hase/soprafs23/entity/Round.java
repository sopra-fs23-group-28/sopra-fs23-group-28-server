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
    private boolean isTimerOver = false;
    @Column
    @ElementCollection
    private List<Categories> categories = new ArrayList<>();
    @Column
    @ElementCollection
    private List<Categories> categoryVotes = new ArrayList<>();
    @Column
    private Categories chosenCategory;
    @Column
    private String currentQuestion;

    @Column
    private Long punishmentSteps;

    @Column
    @ElementCollection
    private List<String> answers;
    @Column
    private Long rightAnswer;
    @Column
    private Long answerCount = 0L;
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
    public boolean isTimerOver() {
        return isTimerOver;
    }
    public void setTimerOver(boolean timerOver) {
        isTimerOver = timerOver;
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }


    public Long getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Long rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Long getAnswerCount() {
        return answerCount;
    }

    public void resetAnswerCount(){this.answerCount = 0L;}
    public void incrementAnswerCount() {
        this.answerCount+=1;
    }

    public Long getPunishmentSteps() {return punishmentSteps;}

    public void setPunishmentSteps(Long punishmentSteps) {this.punishmentSteps = punishmentSteps;}
}
