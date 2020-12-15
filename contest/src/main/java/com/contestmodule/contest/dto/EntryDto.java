package com.contestmodule.contest.dto;

import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.entity.Entry;

public class EntryDto {

    private Long userId, contestId;
    private String videoLink, userComment, horsename;

    public EntryDto() {
    }

    public EntryDto(Long userId, Long contestId, String videoLink, String userComment, String horsename) {
        this.userId = userId;
        this.contestId = contestId;
        this.videoLink = videoLink;
        this.userComment = userComment;
        this.horsename = horsename;
    }

    public Entry getEntryFromEntryDto(Contest contest){
        Entry entry = new Entry();
        entry.setUserId(userId);
        entry.setContest(contest);
        entry.setVideolink(videoLink);
        entry.setUserComment(userComment);
        entry.setHorseName(horsename);

        return entry;
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
}
