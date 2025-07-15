package com.inditex.msvc.price.infrastructure.adapters.utils;

import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.exception.PriceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilMethodsTest {

    @Test
    void validatePrice_Success() {
        Price validPrice = new Price();
        validPrice.setPriceAmount(100.0);

        assertDoesNotThrow(() -> UtilMethods.validatePrice(validPrice));
    }


    @Test
    void validatePriceNullPriceAmount() {
        Price invalidPrice = new Price();
        invalidPrice.setPriceAmount(null);

        PriceException exception = assertThrows(PriceException.class,
                () -> UtilMethods.validatePrice(invalidPrice));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void validatePriceInvalidPriceAmount() {
        Price invalidPrice = new Price();
        invalidPrice.setPriceAmount(-10.0);

        PriceException exception = assertThrows(PriceException.class,
                () -> UtilMethods.validatePrice(invalidPrice));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void validatePriceZeroPriceAmount() {
        Price invalidPrice = new Price();
        invalidPrice.setPriceAmount(0.0);

        PriceException exception = assertThrows(PriceException.class,
                () -> UtilMethods.validatePrice(invalidPrice));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void getUpdatedValueNewValueNotNull() {
        String newValue = "New Value";
        String existingValue = "Existing Value";
        String result = UtilMethods.getUpdatedValue(newValue, existingValue);

        assertEquals(newValue, result);
    }

    @Test
    void getUpdatedValueNewValueNull() {
        String newValue = null;
        String existingValue = "Existing Value";
        String result = UtilMethods.getUpdatedValue(newValue, existingValue);

        assertEquals(existingValue, result);
    }

    @Test
    void getUpdatedValueBothValuesNull() {
        String newValue = null;
        String existingValue = null;
        String result = UtilMethods.getUpdatedValue(newValue, existingValue);

        assertNull(result);
    }

    @Test
    void getUpdatedValueIntegerValues() {
        Integer newValue = 10;
        Integer existingValue = 5;
        Integer result = UtilMethods.getUpdatedValue(newValue, existingValue);
        assertEquals(newValue, result);
        newValue = null;
        result = UtilMethods.getUpdatedValue(newValue, existingValue);

        assertEquals(existingValue, result);
    }
}