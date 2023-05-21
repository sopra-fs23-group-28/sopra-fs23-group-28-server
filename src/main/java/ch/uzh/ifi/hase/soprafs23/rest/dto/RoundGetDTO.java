package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.Categories;

import java.util.ArrayList;
import java.util.List;

public class RoundGetDTO {
    private List<Categories> categories = new ArrayList<>();
    private Categories chosenCategory;
    private List<String> answers = new ArrayList<>();
    private String currentQuestion;
    public List<Categories> getCategories() {
        return categories;
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
}
