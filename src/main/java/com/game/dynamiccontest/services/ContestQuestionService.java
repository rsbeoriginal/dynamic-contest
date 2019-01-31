package com.game.dynamiccontest.services;

import com.game.dynamiccontest.dto.ContestQuestionDTO;
import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.entity.ContestQuestion;
import com.game.dynamiccontest.utils.FailException;

import java.util.List;

public interface ContestQuestionService {
    ContestQuestion add(String contestId, ContestQuestionDTO contestQuestion) throws FailException;
    List<ContestQuestion> getAllQuestions(String contestId);
    ContestQuestion getContestQuestionById(String contestId, String questionId);
    int getNextQuestionNumber(String contestId);
    void deleteContestQuestionByContestId(String contestId);

//    void sendQuestionToFirebaseDatabase(String contestId, String questionSequence, QuestionDetailDTO questionDetailDTO);
}
