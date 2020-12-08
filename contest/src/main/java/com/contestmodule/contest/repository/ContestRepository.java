package com.contestmodule.contest.repository;

import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.entity.SimpleContestDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContestRepository extends CrudRepository <Contest, Long> {
    @Query("SELECT name as name," +
            " description as description," +
            " startDate as startDate," +
            " endDate as endDate," +
            " entryFee as entryFee," +
            " contestLevel as contestLevel," +
            " winningAward as winningAward" +
            " FROM Contest where startDate < ?1 and endDate > ?2")
    Iterable<SimpleContestDao> findAllForUserBetweenDates(LocalDate today, LocalDate copy);
}
