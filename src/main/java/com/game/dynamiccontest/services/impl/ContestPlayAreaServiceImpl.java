package com.game.dynamiccontest.services.impl;

import com.game.dynamiccontest.dto.ContestPlayAreaDTO;
import com.game.dynamiccontest.dto.SubmitContestDTO;
import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.entity.ContestPlayArea;
import com.game.dynamiccontest.entity.ContestQuestion;
import com.game.dynamiccontest.entity.ContestSubscribe;
import com.game.dynamiccontest.repository.ContestPlayAreaRepository;
import com.game.dynamiccontest.repository.ContestQuestionRepository;
import com.game.dynamiccontest.repository.ContestRepository;
import com.game.dynamiccontest.repository.ContestSubscribeRepository;
import com.game.dynamiccontest.services.ContestPlayAreaService;
import com.game.dynamiccontest.services.ContestSubscribeService;
import com.game.dynamiccontest.utils.FailException;
import com.game.dynamiccontest.utils.MicroservicesURL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
public class ContestPlayAreaServiceImpl implements ContestPlayAreaService {

    @Autowired
    ContestPlayAreaRepository contestPlayAreaRepository;

    @Autowired
    ContestSubscribeRepository contestSubscribeRepository;

    @Autowired
    ContestQuestionRepository contestQuestionRepository;

    @Autowired
    ContestRepository contestRepository;

    @Autowired
    ContestSubscribeService contestSubscribeService;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public void submitAnswer(ContestPlayAreaDTO contestPlayAreaDTO) throws FailException {

        //if not subscribed
        if(contestSubscribeRepository.checkContest(contestPlayAreaDTO.getContestId(),contestPlayAreaDTO.getUserId())==0){
            try {
                contestSubscribeService.subscribeToContest(contestPlayAreaDTO.getContestId(),contestPlayAreaDTO.getUserId());
            } catch (FailException e) {
                e.printStackTrace();
            }
        }

        //add contest play area data
        if(checkIfAlreadyExists(contestPlayAreaDTO.getContestId(),contestPlayAreaDTO.getUserId(),contestPlayAreaDTO.getQuestionId())){
            ContestPlayArea contestPlayArea = new ContestPlayArea();
            BeanUtils.copyProperties(contestPlayAreaDTO,contestPlayArea);
            contestPlayArea.setStartTime(getStartTimeFromContest(contestPlayAreaDTO.getContestId(),contestPlayAreaDTO.getQuestionId()));

            //get long time
            Date date = new Date();
            long endTime = date.getTime();
            contestPlayArea.setEndTime(endTime);

            //get Contest Question
            ContestQuestion contestQuestion = contestQuestionRepository.findContestQuestionByContest_ContestIdAndQuestionId(contestPlayAreaDTO.getContestId(),contestPlayAreaDTO.getQuestionId());
            contestPlayArea.setQuestionSequence(contestQuestion.getQuestionSequence());
            if(checkAnswerFromServer(contestPlayAreaDTO.getQuestionId(),contestPlayAreaDTO.getAnswer())) {
                contestPlayArea.setScore(calculateScore(getStartTimeFromContest(contestPlayArea.getContestId(),contestPlayArea.getQuestionId()),endTime));
            }else {
                contestPlayArea.setScore(0d);
            }
            contestPlayAreaRepository.save(contestPlayArea);

            //for last question
            if(checkLastQuestion(contestPlayAreaDTO.getContestId(),contestPlayAreaDTO.getQuestionId())){
                finishContest(contestPlayAreaDTO.getContestId(),contestPlayArea.getUserId());
            }

        }else{
            throw new FailException("Already answered");
        }


    }

    private Double calculateScore(Long startTime, Long endTime) {
        Long diffTime = endTime - startTime;
        Double score = MicroservicesURL.DYNAMIC_QUESTION_MARKS + Double.valueOf("0."+diffTime);
        return score;
    }

    private boolean checkLastQuestion(String contestId, String questionId) {
        ContestQuestion contestQuestion = contestQuestionRepository.findContestQuestionByContest_ContestIdAndQuestionId(contestId,questionId);
        if(null != contestQuestion && contestQuestion.getLast() == true){
            return true;
        }
        return false;
    }

    @Override
    public void finishContest(String contestId, String userId) {
        Double totalScore = contestPlayAreaRepository.getTotalScorebyContest(contestId,userId);
        ContestSubscribe contestSubscribe = contestSubscribeRepository.getContestbyUserId(contestId,userId);
        if(contestSubscribe ==null){
            contestSubscribe = new ContestSubscribe();
            Contest contest = new Contest();
            contest.setContestId(contestId);
            contestSubscribe.setContest(contest);
            contestSubscribe.setUserId(userId);
        }
        contestSubscribe.setScore(totalScore);
        contestSubscribe.setFinished(true);
        contestSubscribeRepository.save(contestSubscribe);
        System.out.println("score: " + totalScore);
        try{
            SubmitContestDTO submitContestDTO = new SubmitContestDTO();
            submitContestDTO.setContestId(contestSubscribe.getContest().getContestId());
            submitContestDTO.setContestName(contestRepository.findOne(contestId).getContestName());
            submitContestDTO.setScore(contestSubscribe.getScore());
            submitContestDTO.setUserId(contestSubscribe.getUserId());
            submitContest(submitContestDTO);
        }catch (Exception e){
            System.out.println("submit contest -> REPORTING exception : " + e.getMessage());
        }
    }

    @Override
    public String getQuestionWinner(String contestId, String questionId) throws FailException{
        List<ContestPlayArea> userList = contestPlayAreaRepository.getWinnerbyContestIdAndQuestionId(contestId, questionId);
        try{
            return userList.get(0).getUserId();
        }catch (Exception e) {
            throw new FailException("No winner");
        }
    }


    public boolean checkAnswerFromServer(String questionId,String optionIds)
    {
        String URL=MicroservicesURL.SCREENED_QUESTION_BASE_URL+ MicroservicesURL.CHECK_ANSWER;
        HashMap<String,String> hash=new HashMap<>();
        hash.put("questionId",questionId);
        hash.put("userAnswer",optionIds);
        HttpEntity<HashMap<String,String>> request=new HttpEntity<>(hash,null);
        ResponseEntity<Boolean> response=restTemplate.postForEntity(URL,request,Boolean.class);
        System.out.println(response.getBody());
        return response.getBody();
    }

    private Long getStartTimeFromContest(String contestId, String questionId) {
        return contestQuestionRepository.getStartTimeByQuestion(contestId,questionId);
    }

    private boolean checkIfAlreadyExists(String contestId, String userId, String questionId) {
        return (contestPlayAreaRepository.checkAlreadyExists(contestId,userId,questionId)==0);
    }

//    private void findWinnerByContestAndQuestion(String contestId,String questionId){
//        String userId = contestPlayAreaRepository.getWinnerbyContestIdAndQuestionId(contestId,questionId);
//        System.out.println("" + userId);
//    }
//
//    private void findContestWinner(String contestId){
//        String userId = contestSubscribeRepository.getWinnerByContestId(contestId);
//    }

    public String submitContest(SubmitContestDTO submitContestDTO) {
        String URL = MicroservicesURL.REPORTING_BASE_URL + MicroservicesURL.ADD_TO_LEADERBOARD;
        ResponseEntity<String> response = restTemplate.postForEntity(URL, submitContestDTO, String.class);
        return response.getBody();
    }


}
