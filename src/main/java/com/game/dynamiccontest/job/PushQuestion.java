package com.game.dynamiccontest.job;

import com.game.dynamiccontest.controller.ContestController;
import com.game.dynamiccontest.dto.OptionDTO;
import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.entity.ContestQuestion;
import com.game.dynamiccontest.services.ContestQuestionService;
import com.game.dynamiccontest.services.impl.ContestQuestionServiceImpl;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PushQuestion implements Job {

    @Inject
    ContestQuestionService contestQuestionService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Scheduler scheduler = jobExecutionContext.getScheduler();
        SchedulerContext schedulerContext = null;
        try{
            schedulerContext = scheduler.getContext();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            String contestId = (String) schedulerContext.get("contestId");
            Integer questionSequence = (Integer) schedulerContext.get("questionSequence");
            System.out.println(contestId + " " + questionSequence);
            ContestQuestion contestQuestion = contestQuestionService.getContestQuestionBySequenceNumber(contestId, questionSequence);
            System.out.println();
//            System.out.println(contestQuestion == null);
//            ContestQuestion contestQuestion = new ContestQuestion();
//            contestQuestion.setQuestionId("1");
            if (contestQuestion == null)
                return;
            else {
                QuestionDetailDTO questionDetailDTO = new QuestionDetailDTO();
                questionDetailDTO.setQuestionId(contestQuestion.getQuestionId());
                System.out.println("SUCCESS");
                //contestQuestionService.sendQuestionToFirebaseDatabase(contestId, questionSequence, questionDetailDTO);
//            contestQuestionService.pushQuestion(contestQuestion.getStartTime()+3*60*1000, contestId, questionSequence+1);
                try {
                    scheduler.deleteJob(JobKey.jobKey("jobPushQuestionToFirebase", "groupPushQuestionToFirebase"));
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
