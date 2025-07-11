package com.inditex.msvc.price.infrastructure.adapters.utils;

import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.exception.PriceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneralMethodsTest {

    @Test
    void validatePrice_Success() {
        Price validPrice = new Price();
        validPrice.setPriceAmount(100.0);

        assertDoesNotThrow(() -> GeneralMethods.validatePrice(validPrice));
    }


    @Test
    void validatePriceNullPriceAmount() {
        Price invalidPrice = new Price();
        invalidPrice.setPriceAmount(null);

        PriceException exception = assertThrows(PriceException.class,
                () -> GeneralMethods.validatePrice(invalidPrice));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void validatePriceInvalidPriceAmount() {
        Price invalidPrice = new Price();
        invalidPrice.setPriceAmount(-10.0);

        PriceException exception = assertThrows(PriceException.class,
                () -> GeneralMethods.validatePrice(invalidPrice));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void validatePriceZeroPriceAmount() {
        Price invalidPrice = new Price();
        invalidPrice.setPriceAmount(0.0);

        PriceException exception = assertThrows(PriceException.class,
                () -> GeneralMethods.validatePrice(invalidPrice));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }
}