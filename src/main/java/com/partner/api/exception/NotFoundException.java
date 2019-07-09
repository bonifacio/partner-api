package com.partner.api.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String className) {
        super(String.format("%s not found", className));
    }
}
