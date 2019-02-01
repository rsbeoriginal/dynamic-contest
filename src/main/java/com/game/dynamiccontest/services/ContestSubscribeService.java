package com.game.dynamiccontest.services;

import com.game.dynamiccontest.dto.ContestSubscriptionDTO;
import com.game.dynamiccontest.entity.ContestSubscribe;
import com.game.dynamiccontest.utils.FailException;

import java.util.List;

public interface ContestSubscribeService {

    ContestSubscribe subscribeToContest(String contestId, String userId) throws FailException;

    List<ContestSubscriptionDTO> getLearboard(String contestId) throws FailException;

    ContestSubscriptionDTO getLearboardByUserId(String contestId, String userId) throws FailException;
}
