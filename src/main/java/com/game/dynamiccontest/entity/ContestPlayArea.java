package com.game.dynamiccontest.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity(name = ContestPlayArea.TABLE_NAME)
@Entity
public class ContestPlayArea {

//    public static final String TABLE_NAME="CONTEST_PLAY_AREA";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String contestPlayAreaId;

    private String userId;
    private String contestId;
    private String questionId;
    private String questionSequence;
    private Long startTime;
    private Long endTime;
    private String answer;
    private Double score;

//    public static String getTableName() {
//        return TABLE_NAME;
//    }

    public String getContestPlayAreaId() {
        return contestPlayAreaId;
    }

    public void setContestPlayAreaId(String contestPlayAreaId) {
        this.contestPlayAreaId = contestPlayAreaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionSequence() {
        return questionSequence;
    }

    public void setQuestionSequence(String questionSequence) {
        this.questionSequence = questionSequence;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ContestPlayArea{" +
                "contestPlayAreaId='" + contestPlayAreaId + '\'' +
                ", userId='" + userId + '\'' +
                ", contestId='" + contestId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", questionSequence='" + questionSequence + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", answer='" + answer + '\'' +
                ", score=" + score +
                '}';
    }
}
