package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.entity.ContestSubscribe;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContestSubscribeRepository extends CrudRepository<ContestSubscribe,String> {

    @Query("SELECT COUNT(*) FROM ContestSubscribe WHERE (contest_id =?1 AND userId = ?2)")
    Integer checkContest(String contestId, String userId);

    @Query("FROM ContestSubscribe WHERE (contest_id =?1 AND userId = ?2))")
    ContestSubscribe getContestbyUserId(String contestId, String userId);

    @Query("FROM ContestSubscribe WHERE (contest_id =?1 AND finished = TRUE) ORDER BY score DESC)")
    List<ContestSubscribe> getLeaderboard(String contestId);

//    @Query("SELECT userId FROM ContestSubscribe WHERE (contestId = ?1) ORDER BY score LIMIT 1")
//    String getWinnerByContestId(String contestId);
}
