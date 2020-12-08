package com.contestmodule.contest.entity;

import com.contestmodule.contest.validator.ContestDateRange;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ContestDateRange
@Entity
@Table(name = "contests")
public class Contest {

    @Id
    @Column(name = "contest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name must not be blank")
    private String name;
    @NotEmpty
    private String description;
    @Min(2)
    private int maxParticipants;
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull
    private BigDecimal entryFee;
    @NotEmpty
    private String contestLevel;

    private String winningAward;
    private String adminComment;

    @OneToMany(
            mappedBy = "contest",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    Set<Entry> entries = new HashSet<>();

    public Contest() {
    }

    public Contest(String name, String description, int maxParticipants, LocalDate startDate, LocalDate endDate, BigDecimal entryFee, String winningAward, String contestLevel, String adminComment) {
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.entryFee = entryFee;
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

    public Set<Entry> getEntries() {
        return entries;
    }
    public void addEntry(Entry entry){
        entries.add(entry);
        entry.setContest(this);
    }
    public void removeEntry(Entry entry){
        entries.remove(entry);
        entry.setContest(null);
    }

    public String getWinningAward() {
        return winningAward;
    }
    public void setWinningAward(String winningAward) {
        this.winningAward = winningAward;
    }

    public String getContestLevel() {
        return contestLevel;
    }
    public void setContestLevel(String contestLevel) {
        this.contestLevel = contestLevel;
    }

    public String getAdminComment() {
        return adminComment;
    }
    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }
}
