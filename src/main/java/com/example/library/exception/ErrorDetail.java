package com.example.library.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDetail {
    private LocalDateTime time;
    private String message;

    public ErrorDetail(String message) {
        this.time = LocalDateTime.now();
        this.message = message;
    }
}
