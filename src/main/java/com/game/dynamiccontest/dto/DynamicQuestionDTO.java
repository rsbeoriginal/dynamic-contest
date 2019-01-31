package com.game.dynamiccontest.dto;

import java.util.List;

public class DynamicQuestionDTO {

    private String questionId;
    private String questionName;
    private String questionContent;
    private List<OptionDTO> optionDTOList;
    //    private List<OptionDTO> correctDTOList;
    private String questionCategory;
    private String questionType;
    private String answerType;
    private String questionDifficulty;
    private int duration;
    private Boolean isLast;
    private Integer questionSequence;

    public String getQuestionId() {
        return questionId;
    }


    public void setQuestionDetail(QuestionDetailDTO questionDetailDTO) {
        this.questionId = questionDetailDTO.getQuestionId();
        this.questionName = questionDetailDTO.getQuestionName();
        this.questionContent = questionDetailDTO.getQuestionContent();
        this.optionDTOList = questionDetailDTO.getOptionDTOList();
        this.questionCategory = questionDetailDTO.getQuestionCategory();
        this.questionType = questionDetailDTO.getQuestionType();
        this.answerType = questionDetailDTO.getAnswerType();
        this.questionDifficulty = questionDetailDTO.getQuestionDifficulty();
        this.duration = questionDetailDTO.getDuration();
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public List<OptionDTO> getOptionDTOList() {
        return optionDTOList;
    }

    public void setOptionDTOList(List<OptionDTO> optionDTOList) {
        this.optionDTOList = optionDTOList;
    }

    public String getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Boolean getLast() {
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }

    public Integer getQuestionSequence() {
        return questionSequence;
    }

    public void setQuestionSequence(Integer questionSequence) {
        this.questionSequence = questionSequence;
    }

    @Override
    public String toString() {
        return "DynamicQuestionDTO{" +
        "questionId='" + questionId + '\'' +
        ", questionName='" + questionName + '\'' +
        ", questionContent='" + questionContent + '\'' +
        ", optionDTOList=" + optionDTOList +
        ", questionCategory='" + questionCategory + '\'' +
        ", questionType='" + questionType + '\'' +
        ", answerType='" + answerType + '\'' +
        ", questionDifficulty='" + questionDifficulty + '\'' +
        ", duration=" + duration +
        ", isLast=" + isLast +
        ", questionSequence=" + questionSequence +
                '}';
    }

}
