package com.game.dynamiccontest.services;

import com.game.dynamiccontest.entity.ContestSubscribe;
import com.game.dynamiccontest.utils.FailException;

public interface ContestSubscribeService {

    ContestSubscribe subscribeToContest(String contestId, String userId) throws FailException;

}
