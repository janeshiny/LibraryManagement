package com.example.library.exception;

import com.example.library.model.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<Void>> handleBookDetailInvalidException(BookDetailInvalidException exception){
        ApiResponse<Void> response= ApiResponse.error(null,exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBookNotFoundException(BookNotFoundException exception){
        ApiResponse<Void> response= ApiResponse.error(null, exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleFieldValueException(MethodArgumentNotValidException exception){
        Map<String, String> err = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->{
            String fieldName= error.getField();
            String errorMessage=error.getDefaultMessage();
            err.put(fieldName,errorMessage);
                }
        );
        ApiResponse<Map<String,String>> response= ApiResponse.error(err, exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleRemainingException(Exception exception){
        ApiResponse<Void> response= ApiResponse.error(null, exception.getMessage());
        log.error(exception.getMessage(),exception);
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
