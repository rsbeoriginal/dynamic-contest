package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.entity.Contest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends CrudRepository<Contest,String> {
}
