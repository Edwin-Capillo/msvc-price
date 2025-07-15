package com.inditex.msvc.price.infrastructure.adapters.utils;

import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.exception.PriceException;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class UtilMethods {
    private UtilMethods() {
    }
    public static void validatePrice(Price price) {
        Objects.requireNonNull(price, ConstantsUtils.EMPTY_OR_NULL);
        if (price.getPriceAmount() == null || price.getPriceAmount().compareTo(0.0) <= 0) {
            throw new PriceException(HttpStatus.BAD_REQUEST, ConstantsUtils.PRICE_AMOUNT_INVALID
                    + price.getPriceAmount());
        }
    }

    public static <T> T getUpdatedValue(T newValue, T existingValue) {
        return newValue != null ? newValue : existingValue;
    }
}
