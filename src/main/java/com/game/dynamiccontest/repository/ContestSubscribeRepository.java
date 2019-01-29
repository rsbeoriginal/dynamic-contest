package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.entity.ContestSubscribe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ContestSubscribeRepository extends CrudRepository<ContestSubscribe,String> {

    @Query("SELECT COUNT(*) FROM ContestSubscribe WHERE (contest_id =?1 AND userId = ?2)")
    Integer checkContest(String contestId, String userId);
}
