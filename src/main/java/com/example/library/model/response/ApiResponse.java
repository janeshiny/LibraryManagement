package com.example.library.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final T data;
    private final Boolean success;
    private final String message;
    private final LocalDateTime time;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, true, message, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(T data, String message) {
        return new ApiResponse<>(data, false, message, LocalDateTime.now());
    }
}
