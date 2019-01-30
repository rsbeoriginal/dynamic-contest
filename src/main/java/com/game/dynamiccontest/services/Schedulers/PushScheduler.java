package com.game.dynamiccontest.services.Schedulers;

import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.entity.ContestQuestion;
import com.game.dynamiccontest.services.ContestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

public  class PushScheduler implements Runnable {

    @SuppressWarnings("rawtypes")
    ScheduledFuture scheduledFuture;

    TaskScheduler taskScheduler ;

    //this method will kill previous scheduler if exists and will create a new scheduler with given cron expression
    public  void reSchedule(String cronExpressionStr){
        if(taskScheduler== null){
            this.taskScheduler = new ConcurrentTaskScheduler();
        }
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
        }
        this.scheduledFuture = this.taskScheduler.schedule(this, new CronTrigger(cronExpressionStr));
        System.out.println("ABC");
    }

    String contestId;
    Integer questionSequence;
    String cronExp;

    @Autowired
    ContestQuestionService contestQuestionService;

    @Override
    public  void run(){
        ContestQuestion contestQuestion = contestQuestionService.getContestQuestionBySequenceNumber(this.contestId, this.questionSequence);
        System.out.println(contestQuestion.getQuestionId());
        if (contestQuestion == null)
            return;
        else {
            QuestionDetailDTO questionDetailDTO = new QuestionDetailDTO();
            questionDetailDTO.setQuestionId(contestQuestion.getQuestionId());
            System.out.println("SUCCESS");
            //contestQuestionService.sendQuestionToFirebaseDatabase(contestId, questionSequence, questionDetailDTO);
//            contestQuestionService.pushQuestion(contestQuestion.getStartTime()+3*60*1000, contestId, questionSequence+1);
        }
    }

    public void initializeScheduler() {
        this.reSchedule(cronExp);
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public Integer getQuestionSequence() {
        return questionSequence;
    }

    public void setQuestionSequence(Integer questionSequence) {
        this.questionSequence = questionSequence;
    }

    public String getCronExp() {
        return cronExp;
    }

    public void setCronExp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat ("ss mm HH * * ? *");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        String cron = dateFormat.format(date);
        this.cronExp = cron;
    }
}
