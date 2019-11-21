package com.geekbrains.erth.tracker.exceptions;

public class TaskExistsException extends RuntimeException{

    public TaskExistsException(String message) {
        super(message);
    }
    
}
