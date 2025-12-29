package com.example.library.exception;

public class BookDetailInvalidException extends RuntimeException {
    public BookDetailInvalidException(String message) {
        super(message);
    }

    public BookDetailInvalidException(String message,Throwable cause){
        super(message,cause);
    }
}
