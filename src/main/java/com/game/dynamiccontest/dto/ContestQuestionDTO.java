package com.game.dynamiccontest.dto;

import com.game.dynamiccontest.entity.Contest;

public class ContestQuestionDTO {
    private String contestQuestionId;
    private Contest contest;
    private String questionId;
    private Integer questionSequence;
    private Long startTime;
    private Boolean isLast;

    public String getContestQuestionId() {
        return contestQuestionId;
    }

    public void setContestQuestionId(String contestQuestionId) {
        this.contestQuestionId = contestQuestionId;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionSequence() {
        return questionSequence;
    }

    public void setQuestionSequence(Integer questionSequence) {
        this.questionSequence = questionSequence;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Boolean getLast() {
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }

    @Override
    public String toString() {
        return "ContestQuestionDTO{" +
                "contestQuestionId='" + contestQuestionId + '\'' +
                ", contest=" + contest +
                ", questionId='" + questionId + '\'' +
                ", questionSequence=" + questionSequence +
                ", startTime=" + startTime +
                ", isLast=" + isLast +
                '}';
    }
}
