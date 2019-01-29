package com.game.dynamiccontest.services;

import com.game.dynamiccontest.entity.ContestQuestion;

import java.util.List;

public interface ContestQuestionService {
    ContestQuestion add(ContestQuestion contestQuestion);
    List<ContestQuestion> getAllQuestions(String contestId);
    ContestQuestion getContestQuestionById(String contestId, String questionId);
    int getNextQuestionNumber(String contestId);
    void deleteContestQuestionByContestId(String contestId);
}
