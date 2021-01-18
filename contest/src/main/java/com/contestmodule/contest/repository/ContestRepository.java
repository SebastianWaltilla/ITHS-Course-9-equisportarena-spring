package com.contestmodule.contest.repository;

import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.dto.ContestWithEntrySummationDto;
import com.contestmodule.contest.entity.Contest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContestRepository extends CrudRepository <Contest, Long> {

    @Query(value = "SELECT new com.contestmodule.contest.dto.ContestInfoForUserDto(" +
            "c.name, " +
            "c.description, " +
            "c.startDate, " +
            "c.endDate, " +
            "c.entryFee, " +
            "c.contestLevel, " +
            "c.winningAward, " +
            "(c.entries.size < c.maxParticipants))" +
            "FROM Contest c where c.startDate <= :today and c.endDate >= :copy ")
    List<ContestInfoForUserDto> findAllActiveContests(LocalDate today, LocalDate copy);


    @Query(value = "SELECT new com.contestmodule.contest.dto.ContestInfoForUserDto(" +
            "c.name, " +
            "c.description, " +
            "c.startDate, " +
            "c.endDate, " +
            "c.entryFee, " +
            "c.contestLevel, " +
            "c.winningAward, " +
            "(c.entries.size < c.maxParticipants))" +
            "FROM Contest c where c.startDate <= :today and c.endDate >= :today and c.id = :id")
    Optional<ContestInfoForUserDto> findContestInfoForUserById(Long id, LocalDate today);

    @Query(value = "SELECT new com.contestmodule.contest.dto.ContestWithEntrySummationDto(" +
            "c.id," +
            "c.name, " +
            "c.description, " +
            "c.maxParticipants, " +
            "c.startDate, " +
            "c.endDate, " +
            "c.entryFee, " +
            "c.contestLevel, " +
            "c.winningAward, " +
            "c.adminComment," +
            "c.entries.size)" +
            " FROM Contest c WHERE c.id = :id")
    Optional<ContestWithEntrySummationDto> findContestWithEntrySummationDto(Long id);
}