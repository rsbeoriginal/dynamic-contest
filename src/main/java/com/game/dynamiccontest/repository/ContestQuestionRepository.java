package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.entity.ContestQuestion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestQuestionRepository extends CrudRepository<ContestQuestion,String> {

    @Query("SELECT startTime FROM ContestQuestion WHERE (contest_id = ?1 AND questionId = ?2)")
    Long getStartTimeByQuestion(String contestId, String questionId);

    List<ContestQuestion> findContestQuestionByContest_ContestId(String contestId);

    ContestQuestion findContestQuestionByContest_ContestIdAndQuestionId(String contestId, String questionId);

    @Query("SELECT COALESCE(MAX(cq.questionSequence),0) FROM ContestQuestion cq WHERE cq.contest.contestId=?1")
    int findNextQuestionNumber(String contestId);

//    @Modifying
//    @Query("DELETE FROM CONTEST_QUESTION cq WHERE cq.contest.contestId=?1")
//    void deleteContestQuestionByContestId(String contestId);

    void deleteContestQuestionByContest_ContestId(String contestId);
}
