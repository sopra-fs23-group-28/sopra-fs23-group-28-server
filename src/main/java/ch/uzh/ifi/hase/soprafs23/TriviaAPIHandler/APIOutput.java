package ch.uzh.ifi.hase.soprafs23.TriviaAPIHandler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class APIOutput {
    @JsonProperty("category")
    private String category;

    @JsonProperty("difficulty")
    private String difficulty;

    @JsonProperty("correctAnswer")
    private String correctAnswer;

    @JsonProperty("incorrectAnswers")
    private List<String> incorrectAnswers;

    @JsonProperty("question")
    private APIOutputQuestion apiOutputQuestion;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer.replaceAll("á", "");
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public APIOutputQuestion getApiOutputQuestion() {
        return apiOutputQuestion;
    }

    public void setApiOutputQuestion(APIOutputQuestion apiOutputQuestion) {
        this.apiOutputQuestion = apiOutputQuestion;
    }
}
