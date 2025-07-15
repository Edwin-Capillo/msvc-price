package com.inditex.msvc.price.infrastructure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
    private String details;
    private LocalDateTime timestamp;
    private String path;
}