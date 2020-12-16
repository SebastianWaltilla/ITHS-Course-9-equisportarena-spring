package com.contestmodule.contest.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class EntryNotFoundException extends RuntimeException{

    public  EntryNotFoundException(String message){
        super(message);
    }
}

