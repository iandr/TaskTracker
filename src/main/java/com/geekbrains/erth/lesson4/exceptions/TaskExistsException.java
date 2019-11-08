package com.geekbrains.erth.lesson4.exceptions;

public class TaskExistsException extends RuntimeException{

    public TaskExistsException(String message) {
        super(message);
    }
    
}
