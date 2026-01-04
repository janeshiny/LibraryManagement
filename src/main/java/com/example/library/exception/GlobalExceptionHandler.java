package com.example.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BookDetailInvalidException.class)
    public ResponseEntity<ErrorDetail> handleBookDetailInvalidException(BookDetailInvalidException exception){
        return new ResponseEntity<>(new ErrorDetail(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleBookNotFoundException(BookNotFoundException exception){
        return new ResponseEntity<>(new ErrorDetail(exception.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleFieldValueException(MethodArgumentNotValidException exception){
        Map<String, String> err = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->{
            String fieldName= error.getField();
            String errorMessage=error.getDefaultMessage();
            err.put(fieldName,errorMessage);
                }
        );
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleRemainingException(Exception exception){
        log.error(exception.getMessage(),exception);
        return new ResponseEntity<>(new ErrorDetail(exception.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
