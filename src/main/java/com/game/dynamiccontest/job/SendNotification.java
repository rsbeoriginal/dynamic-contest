package com.game.dynamiccontest.job;

import org.quartz.*;

public class SendNotification implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Scheduler scheduler = jobExecutionContext.getScheduler();
        System.out.println("X");
        //TODO:Add Notification Function

        try {
            scheduler.deleteJob(JobKey.jobKey("jobSendContestNotification", "groupSendContestNotification"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
