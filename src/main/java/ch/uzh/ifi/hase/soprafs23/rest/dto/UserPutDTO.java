package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class UserPutDTO {
    private float time;

    private String token;
    private Long answerIndex;


    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public Long getAnswerIndex() {
        return answerIndex;
    }
    public void setAnswerIndex(Long answerIndex) {
         this.answerIndex = answerIndex;
    }

    public void setQuestionIndex(Long questionIndex) {
        this.answerIndex = questionIndex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
