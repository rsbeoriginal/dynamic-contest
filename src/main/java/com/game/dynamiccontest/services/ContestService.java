package com.game.dynamiccontest.services;

import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.utils.FailException;

import java.util.List;

public interface ContestService {
    Contest add(Contest contest) throws FailException, Exception;

    List<Contest> getAll() throws Exception;

    Contest getContestById(String contestId);
}
