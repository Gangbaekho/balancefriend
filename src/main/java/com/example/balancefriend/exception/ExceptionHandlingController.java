package com.example.balancefriend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(CUserAlreadyExistException.class)
    public ResponseEntity<?> handleConflictException (CUserAlreadyExistException e){

        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }








}