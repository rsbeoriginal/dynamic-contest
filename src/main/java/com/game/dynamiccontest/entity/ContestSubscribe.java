package com.game.dynamiccontest.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = ContestSubscribe.TABLE_NAME)
public class ContestSubscribe {

    public static final String TABLE_NAME="CONTEST_SUBSCRIBE";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String contestSubscribeId;

    private String userId;
    private String contestId;
    private Boolean finished;
    private Double score;

    public static String getTableName() {
        return TABLE_NAME;
    }

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

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
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
}
