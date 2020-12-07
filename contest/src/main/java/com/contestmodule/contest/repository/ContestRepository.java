package com.contestmodule.contest.repository;

import com.contestmodule.contest.entity.Contest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContestRepository extends CrudRepository <Contest, Long> {
    @Query("SELECT name, description, startDate, endDate, entryFee, contestLevel, winningAward FROM Contest where startDate < ?1 and endDate > ?2")
    Iterable<Contest> findAllForUserBetweenDates(LocalDate today, LocalDate copy);
}
