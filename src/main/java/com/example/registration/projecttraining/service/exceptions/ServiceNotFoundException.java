package com.example.registration.projecttraining.service.exceptions;

public class ServiceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public ServiceNotFoundException(String msg){
        super(msg);
    }
}
