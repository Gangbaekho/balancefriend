package com.example.balancefriend.exception;

public class CUserAuthorizationException extends RuntimeException{

    public CUserAuthorizationException(String message){
        super(message);
    }

    public CUserAuthorizationException(String message, Throwable cause){
        super(message, cause);
    }
}
