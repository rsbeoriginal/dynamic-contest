package com.game.dynamiccontest.dto;

import com.game.dynamiccontest.entity.Contest;

public class ContestSubscriptionDTO {
    private String contestSubscribeId;

    private String userId;
    private Contest contest;
    private Boolean finished;
    private Double score;

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
        return "ContestSubscriptionDTO{" +
                "contestSubscribeId='" + contestSubscribeId + '\'' +
                ", userId='" + userId + '\'' +
                ", contest=" + contest +
                ", finished=" + finished +
                ", score=" + score +
                '}';
    }
}
