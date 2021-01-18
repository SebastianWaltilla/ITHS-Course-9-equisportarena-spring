package com.contestmodule.contest.dto;

public class EntryWithContestNameDto {

    private Long userId, contestId;
    private String videoLink, userComment, horsename, contestName;

    public EntryWithContestNameDto() {
    }

    public EntryWithContestNameDto(
            Long userId,
            Long contestId,
            String videolink,
            String userComment,
            String horseName,
            String contestName) {
        this.userId = userId;
        this.contestId = contestId;
        this.videoLink = videolink;
        this.userComment = userComment;
        this.horsename = horseName;
        this.contestName = contestName;
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

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }
}
