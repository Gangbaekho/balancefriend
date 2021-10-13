package com.example.balancefriend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CUserAlreadyExistException extends RuntimeException {

    public CUserAlreadyExistException(String message){
        super(message);
    }

    public CUserAlreadyExistException(String message, Throwable cause){
        super(message, cause);
    }
}
