package com.game.dynamiccontest.services.impl;

import com.game.dynamiccontest.dto.ContestSubscriptionDTO;
import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.entity.ContestSubscribe;
import com.game.dynamiccontest.repository.ContestSubscribeRepository;
import com.game.dynamiccontest.services.ContestSubscribeService;
import com.game.dynamiccontest.utils.FailException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            contestSubscribe.setFinished(new Boolean(false));
            contestSubscribe.setScore(0d);
            contestSubscribeRepository.save(contestSubscribe);
//            System.out.println("finished: " + contestSubscribe.getFinished() + " : " + contestSubscribeRepository.save(contestSubscribe).getFinished());
        }else{
            throw new FailException("Already subscribed");
        }
        return contestSubscribe;
    }

    @Override
    public List<ContestSubscriptionDTO> getLearboard(String contestId) throws FailException {
        List<ContestSubscribe> contestSubscribeList = contestSubscribeRepository.getLeaderboard(contestId);
        List<ContestSubscriptionDTO> contestSubscriptionDTOList = new ArrayList<>();
        if(contestSubscribeList!=null){
            for(int i=0;i<contestSubscribeList.size();i++){
                ContestSubscriptionDTO contestSubscriptionDTO = new ContestSubscriptionDTO();
                BeanUtils.copyProperties(contestSubscribeList.get(i),contestSubscriptionDTO);
                contestSubscriptionDTOList.add(contestSubscriptionDTO);
            }
            return contestSubscriptionDTOList;
        }else{
            throw new FailException("No winners");
        }
    }

    @Override
    public ContestSubscriptionDTO getLearboardByUserId(String contestId, String userId) throws FailException {
        ContestSubscribe contestSubscribe = contestSubscribeRepository.getContestbyUserId(contestId,userId);
        if(contestSubscribe!=null){
            ContestSubscriptionDTO contestSubscriptionDTO = new ContestSubscriptionDTO();
            BeanUtils.copyProperties(contestSubscribe,contestSubscriptionDTO);
            return contestSubscriptionDTO;
        }else{
            throw new FailException("No winners");
        }
    }

    private boolean checkIfAlreadySubscribed(String contestId, String userId) {
        return (contestSubscribeRepository.checkContest(contestId,userId)==0);
    }
}
