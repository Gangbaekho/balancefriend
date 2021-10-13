package com.example.balancefriend.exception;


public class CTodoNotFoundException extends RuntimeException{

    public CTodoNotFoundException(String message){
        super(message);
    }

    public CTodoNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
