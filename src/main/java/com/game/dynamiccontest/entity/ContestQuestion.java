package com.game.dynamiccontest.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//@Entity(name = ContestQuestion.TABLE_NAME)
@Entity
public class ContestQuestion {

//    public static final String TABLE_NAME="CONTEST_QUESTION";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String contestQuestionId;

    @ManyToOne
    @JoinColumn(name = "contest_id")
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

//    public static String getTableName() {
//        return TABLE_NAME;
//    }
}
