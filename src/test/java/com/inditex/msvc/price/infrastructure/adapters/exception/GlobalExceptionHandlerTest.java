package com.inditex.msvc.price.infrastructure.adapters.exception;

import com.inditex.msvc.price.infrastructure.controller.dto.ErrorResponse;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandlePriceException() {

        PriceException priceException = new PriceException(HttpStatus.NOT_FOUND, "Precio no encontrado");
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/price/1");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handlePriceException(priceException, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getCode());
        assertEquals("Precio no encontrado", response.getBody().getMessage());
        assertEquals("/api/price/1", response.getBody().getPath());
    }

    @Test
    void testHandleGeneral() {

        Exception exception = new Exception("Se produjo un error inesperado");
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/general");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneral(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getCode());
        assertEquals(ConstantsUtils.UNEXPECTED_ERROR, response.getBody().getMessage());
        assertEquals("Se produjo un error inesperado", response.getBody().getDetails());
        assertEquals("/api/general", response.getBody().getPath());
    }
}