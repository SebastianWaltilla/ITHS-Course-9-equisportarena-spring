package com.contestmodule.contest.repository;

import com.contestmodule.contest.entity.Contest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContestRepository extends CrudRepository <Contest, Long> {



    List<Contest> findAllByStartDateBeforeAndEndDateAfter(LocalDate todaysDate, LocalDate todaysDate2);
}
