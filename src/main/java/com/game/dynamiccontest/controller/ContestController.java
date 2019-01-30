package com.game.dynamiccontest.controller;

import com.game.dynamiccontest.dto.*;
import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.job.PushQuestion;
import com.game.dynamiccontest.job.SendNotification;
import com.game.dynamiccontest.services.ContestQuestionService;
import com.game.dynamiccontest.services.ContestService;
import com.game.dynamiccontest.services.Schedulers.PushScheduler;
import com.game.dynamiccontest.utils.FailException;
import com.game.dynamiccontest.utils.ResponseConstants;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/contests")
public class ContestController {

    @Autowired
    ContestService contestService;

    @Autowired
    ContestQuestionService contestQuestionService;

    @PostMapping("/")
    public ResponseDTO<ContestDTO> add(@RequestBody RequestDTO<ContestDTO> requestDTO){

        ResponseDTO<ContestDTO> responseDTO = new ResponseDTO<>();
        if(verifyUser(requestDTO.getUserId())) {
            Contest contest = new Contest();
            BeanUtils.copyProperties(requestDTO.getRequest(), contest);
            try {
                ContestDTO contestDTO = new ContestDTO();
                contest.setStartTime(System.currentTimeMillis() + 1*60*1000);
                BeanUtils.copyProperties(contestService.add(contest), contestDTO);
                responseDTO.setStatus(ResponseConstants.SUCCESS);
                responseDTO.setResponse(contestDTO);

                //Schedule Notification
                contestService.sendContestScheduleNotification(contestDTO.getStartTime() - 1*30*1000);
//                createContest(contest.getStartTime() - 1*60*1000);
                contestQuestionService.pushQuestion(contest.getStartTime(), contest.getContestId(), 1);
//                PushScheduler pushScheduler = new PushScheduler();
//                pushScheduler.setContestId(contest.getContestId());
//                pushScheduler.setQuestionSequence(1);
//                pushScheduler.setCronExp(contest.getStartTime());
//                pushScheduler.initializeScheduler();

            } catch (FailException e) {
                responseDTO.setStatus(ResponseConstants.FAIL);
                responseDTO.setErrorMessage(e.getMessage());
            } catch (Exception e) {
                responseDTO.setStatus(ResponseConstants.ERROR);
                responseDTO.setErrorMessage(e.getMessage());
            }
        }else {
            responseDTO.setStatus(ResponseConstants.FAIL);
            responseDTO.setErrorMessage("Auth failed");
        }
        return responseDTO;
    }

    @GetMapping("/")
    public ResponseListDTO<ContestDTO> getAll(){
        ResponseListDTO<ContestDTO> responseListDTO = new ResponseListDTO<>();
        try {
            List<ContestDTO> contestDTOList = new ArrayList<>();
            for (Contest c: contestService.getAll()) {
                ContestDTO contestDTO = new ContestDTO();
                BeanUtils.copyProperties(c,contestDTO);
                contestDTOList.add(contestDTO);
            }
            responseListDTO.setResponse(contestDTOList);
            responseListDTO.setStatus(ResponseConstants.SUCCESS);
        }catch (Exception e){
            responseListDTO.setStatus(ResponseConstants.ERROR);
            responseListDTO.setErrorMessage(e.getMessage());
        }
        return responseListDTO;
    }

    @GetMapping("/{contestId}")
    public ResponseDTO<ContestDTO> getContestById(@PathVariable("contestId") String contestId){
        ResponseDTO<ContestDTO> responseDTO = new ResponseDTO<>();
        try {
            ContestDTO contestDTO = new ContestDTO();
            BeanUtils.copyProperties(contestService.getContestById(contestId),contestDTO);
            responseDTO.setResponse(contestDTO);
            responseDTO.setStatus(ResponseConstants.SUCCESS);
        }catch (Exception e){
            responseDTO.setStatus(ResponseConstants.ERROR);
            responseDTO.setStatus(e.getMessage());
        }
        return responseDTO;
    }

    private boolean verifyUser(String userId) {
        return true;
    }

    //@RequestMapping(value = "/createContest")
    public void createContest(long timestamp){
        try{
            Date date = new Date(timestamp);
            SimpleDateFormat dateFormat = new SimpleDateFormat ("ss mm HH * * ? *");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
            String cron = dateFormat.format(date);
            System.out.println(cron);
//            String cron = "00 00 22 * * ? *";
            JobDetail jobSendContestNotification = JobBuilder.newJob(SendNotification.class)
                    .withIdentity("jobSendContestNotification", "groupSendContestNotification")
                    .build();
            Trigger triggerContestNotification = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .withIdentity("triggerContestNotification", "groupContestNotification")
                    .build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobSendContestNotification, triggerContestNotification);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

//    public void pushQuestion(long timestamp, String contestId, Integer questionSequence){
//        try{
//            Date date = new Date(timestamp);
//            SimpleDateFormat dateFormat = new SimpleDateFormat ("ss/10 mm HH * * ? *");
//            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
//            String cron = dateFormat.format(date);
//            System.out.println(cron);
//            JobDetail jobPushQuestionToFirebase = JobBuilder.newJob(PushQuestion.class)
//                    .withIdentity("jobPushQuestionToFirebase", "groupPushQuestionToFirebase")
//                    .build();
//            Trigger triggerPushQuestionToFirebase = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cron))
//                    .withIdentity("triggerPushQuestionToFirebase", "groupPushQuestionToFirebase")
//                    .build();
//            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//            scheduler.getContext().put("contestId", contestId);
//            scheduler.getContext().put("questionSequence", questionSequence);
//            scheduler.start();
//            scheduler.scheduleJob(jobPushQuestionToFirebase, triggerPushQuestionToFirebase);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }
}
