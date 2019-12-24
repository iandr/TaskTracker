package com.geekbrains.server.exceptions;

public class AlreadyExistsException extends RestResourceException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}