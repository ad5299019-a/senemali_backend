package com.senemali.api.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final boolean success;
    private final int status;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;
}