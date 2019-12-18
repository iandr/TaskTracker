package com.geekbrains.server.exceptions;

public class TaskExistsException extends RuntimeException{

    public TaskExistsException(String message) {
        super(message);
    }
    
}
