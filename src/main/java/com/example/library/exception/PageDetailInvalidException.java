package com.example.library.exception;

public class PageDetailInvalidException extends RuntimeException {
    public PageDetailInvalidException(String message) {
        super(message);
    }
}
