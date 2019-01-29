package com.game.dynamiccontest.services.impl;

import com.game.dynamiccontest.dto.ContestPlayAreaDTO;
import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.entity.ContestPlayArea;
import com.game.dynamiccontest.entity.ContestSubscribe;
import com.game.dynamiccontest.repository.ContestPlayAreaRepository;
import com.game.dynamiccontest.repository.ContestQuestionRepository;
import com.game.dynamiccontest.repository.ContestSubscribeRepository;
import com.game.dynamiccontest.services.ContestPlayAreaService;
import com.game.dynamiccontest.services.ContestSubscribeService;
import com.game.dynamiccontest.utils.FailException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    ContestSubscribeService contestSubscribeService;


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
            contestPlayArea.setStartTime(getStartFromContest(contestPlayAreaDTO.getContestId(),contestPlayAreaDTO.getQuestionId()));

            //get long time
            Date date = new Date();
            long time = date.getTime();
            contestPlayArea.setEndTime(time);

            contestPlayArea.setScore(checkAnswer(contestPlayAreaDTO.getAnswer()));
            contestPlayAreaRepository.save(contestPlayArea);

            //for last question
            if(checkLastQuestion()){
                finishContest(contestPlayAreaDTO.getContestId(),contestPlayArea.getUserId());
            }

        }else{
            throw new FailException("Already answered");
        }


    }

    private boolean checkLastQuestion() {
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
    }

    //TODO:CHECK CORRECT ANSWER
    private Double checkAnswer(String answer) {
        return 0d;
    }

    private Long getStartFromContest(String contestId, String questionId) {
        return contestQuestionRepository.getStartTimeByQuestion(contestId,questionId);
    }

    private boolean checkIfAlreadyExists(String contestId, String userId, String questionId) {
        return (contestPlayAreaRepository.checkAlreadyExists(contestId,userId,questionId)==0);
    }
}
