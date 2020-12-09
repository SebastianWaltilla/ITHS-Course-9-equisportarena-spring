package com.contestmodule.contest.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContestInfoForUserDao {

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal entryFee;
    private String contestLevel;
    private String winningAward;
    private boolean placesLeft;

    public ContestInfoForUserDao() {

    }

    public ContestInfoForUserDao(String name, String description, LocalDate startDate, LocalDate endDate, BigDecimal entryFee, String contestLevel, String winningAward, boolean placesLeft) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.entryFee = entryFee;
        this.contestLevel = contestLevel;
        this.winningAward = winningAward;
        this.placesLeft = placesLeft;
    }

    public ContestInfoForUserDao(String name, String description){
        this.name = name;
        this.description = description;
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

    public boolean isPlacesLeft() {
        return placesLeft;
    }

    public void setPlacesLeft(boolean placesLeft) {
        this.placesLeft = placesLeft;
    }
}
