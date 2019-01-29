package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.entity.ContestQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ContestQuestionRepository extends CrudRepository<ContestQuestion,String> {

    @Query("SELECT startTime FROM ContestQuestion WHERE (contest_id = ?1 AND questionId = ?2)")
    Long getStartTimeByQuestion(String contestId, String questionId);
}
