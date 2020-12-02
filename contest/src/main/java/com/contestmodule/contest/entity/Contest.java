package com.contestmodule.contest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contests")
public class Contest {

    @Id
    @Column(name = "contest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private int maxParticipants;
    @NotEmpty
    private LocalDate startDate;
    @NotEmpty
    private LocalDate endDate;
    @NotEmpty
    private BigDecimal entryFee;
    @NotEmpty
    private String contestLevel;

    private String winningAward;
    private String adminComment;

    public Contest() {
    }

    public Contest(String name, String description, int maxParticipants, LocalDate startDate, LocalDate endDate, BigDecimal entryFee, String winningAward, String contestLevel, String adminComment){
        this.name         = name;
        this.description  = description;
        this.maxParticipants = maxParticipants;
        this.startDate    = startDate;
        this.endDate      = endDate;
        this.entryFee     = entryFee;
        this.winningAward = winningAward;
        this.contestLevel = contestLevel;
        this.adminComment = adminComment;
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

    public String getWinningAward() {
        return winningAward;
    }

    public void setWinningAward(String winningAward) {
        this.winningAward = winningAward;
    }
}
