package com.game.dynamiccontest.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = Contest.TABLE_NAME)
public class Contest {

    public static final String TABLE_NAME = "CONTEST";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String contestId;

    private String contestName;
    private String type;
    private Long startTime;
    private Boolean active;

    public static String getTableName() {
        return TABLE_NAME;
    }

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
}
