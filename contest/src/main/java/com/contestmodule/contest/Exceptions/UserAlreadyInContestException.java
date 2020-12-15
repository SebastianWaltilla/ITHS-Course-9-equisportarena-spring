package com.contestmodule.contest.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAlreadyInContestException extends RuntimeException {

    public UserAlreadyInContestException(String message) {
        super(message);
    }
}
