package com.example.balancefriend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(CUserNotFoundException.class)
    public ResponseEntity<?> handleCUserNotFoundException (CUserNotFoundException e){

        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(CUserAlreadyExistException.class)
    public ResponseEntity<?> handleCUserAlreadyExistException (CUserAlreadyExistException e){

        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(CTodoNotFoundException.class)
    public ResponseEntity<?> handleCTodoNotFoundException (CTodoNotFoundException e){

        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(CUserAuthorizationException.class)
    public ResponseEntity<?> handleCUserAuthorizationException (CUserAuthorizationException e){

        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}