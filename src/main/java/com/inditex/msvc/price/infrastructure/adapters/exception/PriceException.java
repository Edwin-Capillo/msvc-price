package com.inditex.msvc.price.infrastructure.adapters.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class PriceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus errorCode;
    private String errorMessage;
}
