package com.example.registration.projecttraining.controller.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.example.registration.projecttraining.service.exceptions.DatabaseException;
import com.example.registration.projecttraining.service.exceptions.ServiceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;




@ControllerAdvice
public class ControllerExceptionHandle {
    
    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ServiceNotFoundException exception, HttpServletRequest request){
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Resource not found");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException exception, HttpServletRequest request){
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Database exception ");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    
}
