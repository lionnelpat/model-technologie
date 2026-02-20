package com.modeltechnologie.exception;

public class BootcampNotFoundException extends RuntimeException {
    public BootcampNotFoundException(String message) {
        super(message);
    }
}