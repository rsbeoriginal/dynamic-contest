package com.game.dynamiccontest.services.impl;

import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.job.SendNotification;
import com.game.dynamiccontest.repository.ContestRepository;
import com.game.dynamiccontest.services.ContestService;
import com.game.dynamiccontest.utils.FailException;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
public class ContestServiceImpl implements ContestService {

    @Autowired
    ContestRepository contestRepository;

    @Override
    @Transactional(readOnly = false)
    public Contest add(Contest contest) throws FailException,Exception {
        return contestRepository.save(contest);
    }

    @Override
    public List<Contest> getAll() throws Exception {
        return contestRepository.getAll();
    }

    @Override
    public Contest getContestById(String contestId) {
        return contestRepository.findOne(contestId);
    }

    @Override
    public void sendContestScheduleNotification(long timestamp){
        try{
            Date date = new Date(timestamp);
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat ("ss mm hh dd MM E yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
            //String cron = "0/10 55 19 * * ? *";
            String cron = dateFormat.format(date);
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
