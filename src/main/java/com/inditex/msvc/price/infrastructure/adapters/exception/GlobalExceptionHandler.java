package com.inditex.msvc.price.infrastructure.adapters.exception;

import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import com.inditex.msvc.price.infrastructure.controller.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceException.class)
    public ResponseEntity<ErrorResponse> handlePriceException(PriceException ex, WebRequest request) {
        log.error("Error PriceException: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, WebRequest request) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsUtils.UNEXPECTED_ERROR,
                ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                    WebRequest request) {
        log.error("Error de validación: {}", ex.getMessage(), ex);
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                .orElse(ConstantsUtils.VALIDATION_ERROR);

        return buildErrorResponse(HttpStatus.BAD_REQUEST, ConstantsUtils.VALIDATION_ERROR, details, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex,
                                                                            WebRequest request) {
        log.error("Violación de restricciones: {}", ex.getMessage(), ex);
        String details = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                .orElse(ConstantsUtils.VIOLATION_EXCEPTION);

        return buildErrorResponse(HttpStatus.BAD_REQUEST, ConstantsUtils.VIOLATION_EXCEPTION, details, request);
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