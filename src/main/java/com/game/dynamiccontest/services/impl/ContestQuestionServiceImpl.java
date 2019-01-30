package com.game.dynamiccontest.services.impl;

import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.entity.ContestQuestion;
import com.game.dynamiccontest.job.PushQuestion;
import com.game.dynamiccontest.repository.ContestQuestionRepository;
import com.game.dynamiccontest.services.ContestQuestionService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@Transactional(readOnly =  true, propagation = Propagation.REQUIRES_NEW)
public class ContestQuestionServiceImpl implements ContestQuestionService {

    @Autowired
    ContestQuestionRepository contestQuestionRepository;

    @Override
    @Transactional(readOnly = false)
    public ContestQuestion add(ContestQuestion contestQuestion) {
        return contestQuestionRepository.save(contestQuestion);
    }

    @Override
    public List<ContestQuestion> getAllQuestions(String contestId) {
        return (List)contestQuestionRepository.findContestQuestionByContest_ContestId(contestId);
    }

    @Override
    public ContestQuestion getContestQuestionById(String contestId, String questionId) {
        return contestQuestionRepository.findContestQuestionByContest_ContestIdAndQuestionId(contestId, questionId);
    }

    @Override
    public int getNextQuestionNumber(String contestId) {
        return contestQuestionRepository.findNextQuestionNumber(contestId);
//        return 0;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteContestQuestionByContestId(String contestId) {
        contestQuestionRepository.deleteContestQuestionByContest_ContestId(contestId);
    }

    @Override
    public void sendQuestionToFirebaseDatabase(String contestId, Integer questionSequence, QuestionDetailDTO questionDetailDTO) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Dynamic_Contest")
                .child(contestId)
                .child("" + questionSequence);
        ref.setValueAsync(questionDetailDTO);
    }

    @Override
    public ContestQuestion getContestQuestionBySequenceNumber(String contestId, Integer questionSequence) {
        return contestQuestionRepository.findContestQuestionByContest_ContestIdAndQuestionSequence(contestId, questionSequence);
    }

    @Override
    public void pushQuestion(long timestamp, String contestId, Integer questionSequence){
        try{
            Date date = new Date(timestamp);
            SimpleDateFormat dateFormat = new SimpleDateFormat ("ss/10 mm HH * * ? *");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
            String cron = dateFormat.format(date);
            System.out.println(cron);



            JobDetail jobPushQuestionToFirebase = JobBuilder.newJob(PushQuestion.class)
                    .withIdentity("jobPushQuestionToFirebase", "groupPushQuestionToFirebase")
                    .build();
            Trigger triggerPushQuestionToFirebase = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .withIdentity("triggerPushQuestionToFirebase", "groupPushQuestionToFirebase")
                    .build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.getContext().put("contestId", contestId);
            scheduler.getContext().put("questionSequence", questionSequence);
            scheduler.start();
            scheduler.scheduleJob(jobPushQuestionToFirebase, triggerPushQuestionToFirebase);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
