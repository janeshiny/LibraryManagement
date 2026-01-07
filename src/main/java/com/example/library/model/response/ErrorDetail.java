package com.example.library.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDetail {
    private LocalDateTime time;
    private String message;
    private Boolean success;

    public ErrorDetail(String message) {
        this.time = LocalDateTime.now();
        this.message = message;
        this.success = false;
    }
}
