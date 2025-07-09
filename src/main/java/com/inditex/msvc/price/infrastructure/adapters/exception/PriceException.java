package com.inditex.msvc.price.infrastructure.adapters.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PriceException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus errorCode;
    private String errorMessage;

    public PriceException(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
