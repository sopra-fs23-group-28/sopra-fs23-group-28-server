package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.Categories;

import java.util.ArrayList;
import java.util.List;

public class RoundGetDTO {
    private List<Categories> categories = new ArrayList<>();
    private Categories chosenCategory;
    private List<String> currentQuestions = new ArrayList<>();

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

    public List<String> getCurrentQuestions() {
        return currentQuestions;
    }

    public void setCurrentQuestions(List<String> currentQuestions) {
        this.currentQuestions = currentQuestions;
    }
}
