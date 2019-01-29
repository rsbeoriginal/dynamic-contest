package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.entity.ContestQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestQuestionRepository extends CrudRepository<ContestQuestion,String> {
    List<ContestQuestion> findContestQuestionByContest_ContestId(String contestId);
    ContestQuestion findContestQuestionByContest_ContestIdAndQuestionId(String contestId, String questionId);
    @Query("SELECT COALESCE(MAX(cq.questionSequence),0) FROM CONTEST_QUESTION cq WHERE cq.contest.contestId=?1")
    int findNextQuestionNumber(String contestId);
}
