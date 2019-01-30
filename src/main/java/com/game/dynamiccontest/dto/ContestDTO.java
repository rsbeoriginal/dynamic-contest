package com.game.dynamiccontest.dto;

public class ContestDTO {

    private String contestId;
    private String contestName;
    private String type;
    private Long startTime;
    private Boolean active;

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ContestDTO{" +
                "contestId='" + contestId + '\'' +
                ", contestName='" + contestName + '\'' +
                ", type='" + type + '\'' +
                ", startTime=" + startTime +
                ", active=" + active +
                '}';
    }
}
