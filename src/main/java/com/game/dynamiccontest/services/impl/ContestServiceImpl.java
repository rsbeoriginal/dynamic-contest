package com.game.dynamiccontest.services.impl;

import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.repository.ContestRepository;
import com.game.dynamiccontest.services.ContestService;
import com.game.dynamiccontest.utils.FailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
public class ContestServiceImpl implements ContestService {

    @Autowired
    ContestRepository contestRepository;

    @Override
    @Transactional(readOnly = false)
    public Contest add(Contest contest) throws FailException,Exception {
        return contestRepository.save(contest);
    }

    @Override
    public List<Contest> getAll() throws Exception {
        return contestRepository.getAll();
    }

    @Override
    public Contest getContestById(String contestId) {
        return contestRepository.findOne(contestId);
    }
}
