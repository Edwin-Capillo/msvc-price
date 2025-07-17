package com.inditex.msvc.price.infrastructure.adapters.exception;

import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import com.inditex.msvc.price.infrastructure.controller.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");
    }

    @Test
    void testHandlePriceException() {
        PriceException priceException = new PriceException(HttpStatus.NOT_FOUND, "Precio no encontrado");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handlePriceException(priceException, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertErrorResponse(response.getBody(), 404, "Precio no encontrado",
                null);
    }

    @Test
    void testHandleGeneral() {
        Exception exception = new Exception("Se produjo un error inesperado");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneral(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertErrorResponse(response.getBody(), 500, ConstantsUtils.UNEXPECTED_ERROR,
                "Se produjo un error inesperado");
    }

    @Test
    void testHandleValidationExceptions() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName",
                "Mensaje de validación");
        when(bindingResult.getFieldError()).thenReturn(fieldError);

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleValidationExceptions(exception, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertErrorResponse(response.getBody(), 400,
                ConstantsUtils.VALIDATION_ERROR, "Error de validación.");
    }

    @Test
    void testHandleConstraintViolationException() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path propertyPath = mock(Path.class);
        when(propertyPath.toString()).thenReturn("fieldName");
        when(violation.getPropertyPath()).thenReturn(propertyPath);
        when(violation.getMessage()).thenReturn("Mensaje de restricción");
        ConstraintViolationException exception = new ConstraintViolationException(Set.of(violation));

        ResponseEntity<ErrorResponse> response = globalExceptionHandler
                .handleConstraintViolationException(exception, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertErrorResponse(response.getBody(), 400, ConstantsUtils.VIOLATION_EXCEPTION,
                "fieldName: Mensaje de restricción");
    }

    private void assertErrorResponse(ErrorResponse errorResponse, int expectedCode, String expectedMessage,
                                     String expectedDetails) {
        assertEquals(expectedCode, errorResponse.getCode());
        assertEquals(expectedMessage, errorResponse.getMessage());
        assertEquals(expectedDetails, errorResponse.getDetails());
        assertEquals("/api/test", errorResponse.getPath());
        assertEquals(LocalDateTime.now().getYear(), errorResponse.getTimestamp().getYear());
    }
}