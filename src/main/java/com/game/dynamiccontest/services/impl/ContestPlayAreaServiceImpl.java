package com.game.dynamiccontest.services.impl;

import com.game.dynamiccontest.dto.ContestPlayAreaDTO;
import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.entity.ContestPlayArea;
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
        }else{
            throw new FailException("Already answered");
        }


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
