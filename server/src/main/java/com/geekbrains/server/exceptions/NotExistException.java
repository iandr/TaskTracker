package com.geekbrains.server.exceptions;

public class NotExistException extends RestResourceException {

    public NotExistException(String message) {
        super(message);
    }
    
}
