package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookDetailInvalidException.class)
    public ResponseEntity<ErrorDetail> handleBookDetailInvalidException(BookDetailInvalidException exception){
        return new ResponseEntity<>(new ErrorDetail(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleBookNotFoundException(BookNotFoundException exception){
        return new ResponseEntity<>(new ErrorDetail(exception.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleRemainingException(Exception exception){
        return new ResponseEntity<>(new ErrorDetail(exception.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
