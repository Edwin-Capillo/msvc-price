package com.inditex.msvc.price.infrastructure.adapters.utils;

import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.entity.PriceEntity;
import com.inditex.msvc.price.infrastructure.adapters.exception.PriceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


import static org.junit.jupiter.api.Assertions.*;

class UtilPriceMethodsTest {

    @Test
    void validatePriceSuccess() {
        Price validPrice = new Price();
        validPrice.setPriceAmount(100.0);

        assertDoesNotThrow(() -> UtilPriceMethods.validatePrice(validPrice));
    }



    @Test
    void validatePriceInvalid() {
        Price invalidPrice = new Price();
        invalidPrice.setPriceAmount(-10.0);

        PriceException exception = assertThrows(PriceException.class,
                () -> UtilPriceMethods.validatePrice(invalidPrice));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void validatePriceZero() {
        Price zeroPrice = new Price();
        zeroPrice.setPriceAmount(0.0);

        PriceException exception = assertThrows(PriceException.class,
                () -> UtilPriceMethods.validatePrice(zeroPrice));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void validateAndGetPriceSuccess() {
        Price newPrice = new Price();
        newPrice.setBrandId(2L);
        newPrice.setPriceAmount(200.0);

        PriceEntity existingEntity = PriceEntity.builder()
                .brandId(1L)
                .priceAmount(100.0)
                .build();

        PriceEntity updatedEntity = UtilPriceMethods.validateAndGetPrice(newPrice, existingEntity);

        assertEquals(2L, updatedEntity.getBrandId());
        assertEquals(200.0, updatedEntity.getPriceAmount());
    }

    @Test
    void validateAndGetPriceKeepExistingValues() {
        Price newPrice = new Price();
        PriceEntity existingEntity = PriceEntity.builder()
                .brandId(1L)
                .priceAmount(100.0)
                .build();

        PriceEntity updatedEntity = UtilPriceMethods.validateAndGetPrice(newPrice, existingEntity);

        assertEquals(1L, updatedEntity.getBrandId());
        assertEquals(100.0, updatedEntity.getPriceAmount());
    }

    @Test
    void getUpdatedValueNewValueNotNull() {
        String newValue = "New Value";
        String existingValue = "Existing Value";

        String result = UtilPriceMethods.getUpdatedValue(newValue, existingValue);

        assertEquals(newValue, result);
    }

    @Test
    void getUpdatedValueNewValueNull() {
        String newValue = null;
        String existingValue = "Existing Value";

        String result = UtilPriceMethods.getUpdatedValue(newValue, existingValue);

        assertEquals(existingValue, result);
    }

    @Test
    void getUpdatedValueBothValuesNull() {
        String newValue = null;
        String existingValue = null;

        String result = UtilPriceMethods.getUpdatedValue(newValue, existingValue);

        assertNull(result);
    }
}