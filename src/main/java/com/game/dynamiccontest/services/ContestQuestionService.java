package com.game.dynamiccontest.services;

import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.entity.ContestQuestion;

import java.util.List;

public interface ContestQuestionService {
    ContestQuestion add(ContestQuestion contestQuestion);
    List<ContestQuestion> getAllQuestions(String contestId);
    ContestQuestion getContestQuestionById(String contestId, String questionId);
    int getNextQuestionNumber(String contestId);
    void deleteContestQuestionByContestId(String contestId);

    void sendQuestionToFirebaseDatabase(String contestId, Integer questionSequence, QuestionDetailDTO questionDetailDTO);

    ContestQuestion getContestQuestionBySequenceNumber(String contestId, Integer questionSequence);

    void pushQuestion(long timestamp, String contestId, Integer questionSequence);
}
