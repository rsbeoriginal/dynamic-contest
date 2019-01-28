package com.game.dynamiccontest.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = ContestDetail.TABLE_NAME)
public class ContestDetail {

    public static final String TABLE_NAME = "CONTEST_DETAIL";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String contestDetailId;

    private String contestName;
    private String type;
    private Long startTime;
    private String active;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public String getContestDetailId() {
        return contestDetailId;
    }

    public void setContestDetailId(String contestDetailId) {
        this.contestDetailId = contestDetailId;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
