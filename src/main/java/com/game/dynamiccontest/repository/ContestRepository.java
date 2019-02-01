package com.game.dynamiccontest.repository;

import com.game.dynamiccontest.dto.ContestDTO;
import com.game.dynamiccontest.entity.Contest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestRepository extends CrudRepository<Contest,String> {

    @Query("FROM CONTEST")
    List<Contest> getAll();

    @Modifying
    @Query("UPDATE CONTEST SET active = ?1")
    void changeContestActiveAttribute(boolean active);

    @Query("FROM CONTEST WHERE active = TRUE")
    Contest getActiveContest();
}
