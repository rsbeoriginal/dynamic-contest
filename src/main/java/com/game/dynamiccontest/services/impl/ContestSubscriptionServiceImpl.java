package com.game.dynamiccontest.services.impl;

import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.entity.ContestSubscribe;
import com.game.dynamiccontest.repository.ContestSubscribeRepository;
import com.game.dynamiccontest.services.ContestSubscribeService;
import com.game.dynamiccontest.utils.FailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
public class ContestSubscriptionServiceImpl implements ContestSubscribeService {

    @Autowired
    ContestSubscribeRepository contestSubscribeRepository;

    @Override
    @Transactional(readOnly = false)
    public ContestSubscribe subscribeToContest(String contestId, String userId) throws FailException {
        ContestSubscribe contestSubscribe = new ContestSubscribe();
        if(checkIfAlreadySubscribed(contestId,userId)){
            Contest contest = new Contest();
            contest.setContestId(contestId);
            contestSubscribe.setContest(contest);
            contestSubscribe.setUserId(userId);
            contestSubscribe.setFinished(false);
            contestSubscribe.setScore(0d);
            contestSubscribeRepository.save(contestSubscribe);
        }else{
            throw new FailException("Already subscribed");
        }
        return contestSubscribe;
    }

    private boolean checkIfAlreadySubscribed(String contestId, String userId) {
        return (contestSubscribeRepository.checkContest(contestId,userId)==0);
    }
}
