package com.game.dynamiccontest.dto;

public class SubmitQuestionReportDTO {
    private String questionId;
    private Boolean correctResponse;
    public String getQuestionId() {
        return questionId;
    }
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    public Boolean getCorrectResponse() {
        return correctResponse;
    }
    public void setCorrectResponse(Boolean correctResponse) {
        this.correctResponse = correctResponse;
    }
}
