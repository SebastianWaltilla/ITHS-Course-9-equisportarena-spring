package com.contestmodule.contest.dto;

import java.time.LocalDate;

public class AdminEntryDto {

    private Long userId, contestId;
    private String videoLink, userComment, horsename, adminComment;
    private boolean hasPaid;
    private Integer score;
    private LocalDate submissionDate;

    public AdminEntryDto(){

    }

    public AdminEntryDto(Long userId, Long contestId, String videoLink, String userComment, String horsename, String adminComment, boolean hasPaid, Integer score, LocalDate submissionDate) {
        this.userId = userId;
        this.contestId = contestId;
        this.videoLink = videoLink;
        this.userComment = userComment;
        this.horsename = horsename;
        this.adminComment = adminComment;
        this.hasPaid = hasPaid;
        this.score = score;
        this.submissionDate = submissionDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContestId() {
        return contestId;
    }

    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getHorsename() {
        return horsename;
    }

    public void setHorsename(String horsename) {
        this.horsename = horsename;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public boolean isHasPaid() {
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

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
}
