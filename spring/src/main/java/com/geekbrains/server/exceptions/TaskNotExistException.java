package com.geekbrains.server.exceptions;

public class TaskNotExistException extends RuntimeException {

    public TaskNotExistException(String message) {
        super(message);
    }
    
}
