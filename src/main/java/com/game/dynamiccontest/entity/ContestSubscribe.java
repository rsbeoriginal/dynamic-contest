package com.game.dynamiccontest.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//@Entity(name = ContestSubscribe.TABLE_NAME)
@Entity
public class ContestSubscribe {

//    public static final String TABLE_NAME="CONTEST_SUBSCRIBE";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String contestSubscribeId;

    private String userId;
    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;
    private Boolean finished;
    private Double score;

//    public static String getTableName() {
//        return TABLE_NAME;
//    }

    public String getContestSubscribeId() {
        return contestSubscribeId;
    }

    public void setContestSubscribeId(String contestSubscribeId) {
        this.contestSubscribeId = contestSubscribeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ContestSubscribe{" +
                "contestSubscribeId='" + contestSubscribeId + '\'' +
                ", userId='" + userId + '\'' +
                ", contest=" + contest +
                ", finished=" + finished +
                ", score=" + score +
                '}';
    }
}
