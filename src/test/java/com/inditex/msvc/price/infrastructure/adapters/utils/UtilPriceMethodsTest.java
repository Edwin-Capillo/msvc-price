package com.inditex.msvc.price.infrastructure.adapters.utils;

import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.entity.PriceEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilPriceMethodsTest {

     @Test
    void validateAndGetPriceSuccess() {
        Price newPrice = new Price();
        newPrice.setBrandId(2L);
        newPrice.setPriceAmount(200.0);

        PriceEntity existingEntity = PriceEntity.builder()
                .brandId(1L)
                .priceAmount(100.0)
                .build();

        PriceEntity updatedEntity = UtilPriceMethods.buildPrice(newPrice, existingEntity);

        assertEquals(2L, updatedEntity.getBrandId());
        assertEquals(200.0, updatedEntity.getPriceAmount());
    }

    @Test
    void validateAndGetPriceKeepExistingValues() {
        Price newPrice = new Price();
        newPrice.setBrandId(1L);
        newPrice.setPriceAmount(100.0);
        PriceEntity existingEntity = PriceEntity.builder()
                .build();

        PriceEntity updatedEntity = UtilPriceMethods.buildPrice(newPrice, existingEntity);

        assertEquals(1L, updatedEntity.getBrandId());
        assertEquals(100.0, updatedEntity.getPriceAmount());
    }

}