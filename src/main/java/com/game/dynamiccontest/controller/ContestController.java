package com.game.dynamiccontest.controller;

import com.game.dynamiccontest.job.SendNotification;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contest")
public class ContestController {

    @RequestMapping(value = "/createContest")
    public void createContest(){
        try{
            String cron = "0/10 * 12 * * ? *";
            JobDetail job = JobBuilder.newJob(SendNotification.class).build();
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
