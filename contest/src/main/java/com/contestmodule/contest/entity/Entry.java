package com.contestmodule.contest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Entry {
    @Id
    @Column(name = "entry_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @JsonManagedReference //is the forward part of reference – the one that gets serialized normally. See @JsonBackReference in Entry on field contest. To avoid infinite recursion.
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;

    private String videolink;
    private String userComment;
    private boolean hasPaid;
    private Integer score;
    private String adminComment;
    private LocalDate submissionDate;
    private String horseName;

    public Entry(@NotNull Long userId, @NotNull Contest contest, String userComment, boolean hasPaid, String horseName) {
        this.userId = userId;
        this.contest = contest;
        this.userComment = userComment;
        this.hasPaid = hasPaid;
        this.horseName = horseName;
    }

    public Entry(){

    }

    @PrePersist
    private void getCurrentDate() {
        setSubmissionDate(LocalDate.now());
    }

    public Long getId(){
        return this.id;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    @JsonIgnore
    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public String getVideolink() {
        return videolink;
    }
    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public String getUserComment() {
        return userComment;
    }
    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public boolean hasUserPaid() {
        return hasPaid;
    }
    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAdminComment() {
        return adminComment;
    }
    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }
    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getHorseName() {
        return horseName;
    }
    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        return id != null && id.equals(((Entry) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



}
