package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.entity.ContestQuestion;
import org.springframework.data.repository.CrudRepository;

public interface ContestQuestionRepository extends CrudRepository<ContestQuestion,String> {
}
