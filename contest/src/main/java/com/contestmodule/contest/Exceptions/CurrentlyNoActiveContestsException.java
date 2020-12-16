package com.contestmodule.contest.Exceptions;

public class CurrentlyNoActiveContestsException extends RuntimeException{

    public CurrentlyNoActiveContestsException(String message) {
        super(message);
    }
}
