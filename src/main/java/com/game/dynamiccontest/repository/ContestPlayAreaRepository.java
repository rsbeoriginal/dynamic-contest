package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.entity.ContestPlayArea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ContestPlayAreaRepository extends CrudRepository<ContestPlayArea,String> {

    @Query("SELECT COUNT(*) FROM ContestPlayArea WHERE (contestId = ?1 AND userId = ?2 AND questionId = ?3 )")
    Integer checkAlreadyExists(String contestId, String userId, String questionId);
}
