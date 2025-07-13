package com.inditex.msvc.price.infrastructure.adapters.exception;

import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import com.inditex.msvc.price.infrastructure.controller.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceException.class)
    public ResponseEntity<ErrorResponse> handlePriceException(PriceException ex, WebRequest request) {
        return buildErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsUtils.UNEXPECTED_ERROR,
                ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, String details,
                                                             WebRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .code(status.value())
                .message(message)
                .details(details)
                .timestamp(LocalDateTime.now())
                .path(getPath(request))
                .build();

        return ResponseEntity.status(status).body(error);
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}