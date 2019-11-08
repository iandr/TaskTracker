package com.geekbrains.erth.lesson4.exceptions;

public class TaskNotExistException extends RuntimeException {

    public TaskNotExistException(String message) {
        super(message);
    }
    
}
