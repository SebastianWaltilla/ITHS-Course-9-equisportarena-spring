package com.contestmodule.contest.dto;

import com.contestmodule.contest.entity.Contest;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContestWithEntrySummationDto {

    private Long id;
    private String name;
    private String description;
    private int maxParticipants;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal entryFee;
    private String contestLevel;
    private String winningAward;
    private String adminComment;
    private int numberOfRegistered;

    public ContestWithEntrySummationDto(){

    }

    public ContestWithEntrySummationDto(
                                        Long id,
                                        String name,
                                        String description,
                                        int maxParticipants,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        BigDecimal entryFee,
                                        String contestLevel,
                                        String winningAward,
                                        String adminComment,
                                        int numberOfRegistered) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.entryFee = entryFee;
        this.contestLevel = contestLevel;
        this.winningAward = winningAward;
        this.adminComment = adminComment;
        this.numberOfRegistered = numberOfRegistered;
    }

    public ContestWithEntrySummationDto getContestWithEntrySummationDtoFromContest(Contest contest){
        this.setId(contest.getId());
        this.setName(contest.getName());
        this.setDescription(contest.getDescription());
        this.setMaxParticipants(contest.getMaxParticipants());
        this.setStartDate(contest.getStartDate());
        this.setEndDate(contest.getEndDate());
        this.setEntryFee(contest.getEntryFee());
        this.setContestLevel(contest.getContestLevel());
        this.setWinningAward(contest.getWinningAward());
        this.setAdminComment(contest.getAdminComment());
        this.setNumberOfRegistered(contest.getEntries().size());
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(BigDecimal entryFee) {
        this.entryFee = entryFee;
    }

    public String getContestLevel() {
        return contestLevel;
    }

    public void setContestLevel(String contestLevel) {
        this.contestLevel = contestLevel;
    }

    public String getWinningAward() {
        return winningAward;
    }

    public void setWinningAward(String winningAward) {
        this.winningAward = winningAward;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public int getNumberOfRegistered() {
        return numberOfRegistered;
    }

    public void setNumberOfRegistered(int numberOfRegistered) {
        this.numberOfRegistered = numberOfRegistered;
    }
}
