package com.game.dynamiccontest.services;

import com.game.dynamiccontest.dto.ContestPlayAreaDTO;
import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.utils.FailException;

public interface ContestPlayAreaService {
    void submitAnswer(ContestPlayAreaDTO request) throws FailException;
//    QuestionDetailDTO getNextQuestion();
}
