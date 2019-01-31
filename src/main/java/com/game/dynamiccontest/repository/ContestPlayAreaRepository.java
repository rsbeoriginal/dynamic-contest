package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.entity.ContestPlayArea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContestPlayAreaRepository extends CrudRepository<ContestPlayArea,String> {

    @Query("SELECT COUNT(*) FROM ContestPlayArea WHERE (contestId = ?1 AND userId = ?2 AND questionId = ?3 )")
    Integer checkAlreadyExists(String contestId, String userId, String questionId);

    @Query("SELECT SUM(score) FROM ContestPlayArea WHERE (contestId = ?1 AND userId = ?2)")
    Double getTotalScorebyContest(String contestId, String userId);

    @Query("FROM ContestPlayArea WHERE (contestId = ?1 AND questionId = ?2 AND score>0) ORDER BY score DESC")
    List<ContestPlayArea> getWinnerbyContestIdAndQuestionId(String contestId, String questionId);
}
