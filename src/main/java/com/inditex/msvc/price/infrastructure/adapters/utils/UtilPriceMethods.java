package com.inditex.msvc.price.infrastructure.adapters.utils;

import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.entity.PriceEntity;
import com.inditex.msvc.price.infrastructure.adapters.exception.PriceException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class UtilPriceMethods {
    private UtilPriceMethods() {
    }

    public static void validatePrice(Price price) {
        Objects.requireNonNull(price, ConstantsUtils.EMPTY_OR_NULL);
        if (price.getPriceAmount() == null || price.getPriceAmount().compareTo(0.0) <= 0) {
            throw new PriceException(HttpStatus.BAD_REQUEST, ConstantsUtils.PRICE_AMOUNT_INVALID
                    + price.getPriceAmount());
        }
    }

    public static PriceEntity validateAndGetPrice(Price price, PriceEntity existingEntity) {
        Objects.requireNonNull(price, ConstantsUtils.EMPTY_OR_NULL);
        return existingEntity.toBuilder()
                .brandId(getUpdatedValue(price.getBrandId(), existingEntity.getBrandId()))
                .startDate(getUpdatedValue(price.getStartDate(), existingEntity.getStartDate()))
                .endDate(getUpdatedValue(price.getEndDate(), existingEntity.getEndDate()))
                .priceList(getUpdatedValue(price.getPriceList(), existingEntity.getPriceList()))
                .productId(getUpdatedValue(price.getProductId(), existingEntity.getProductId()))
                .priority(getUpdatedValue(price.getPriority(), existingEntity.getPriority()))
                .priceAmount(getUpdatedValue(price.getPriceAmount(), existingEntity.getPriceAmount()))
                .currency(getUpdatedValue(price.getCurrency(), existingEntity.getCurrency()))
                .lastUpdated(LocalDateTime.now())
                .build();
    }

    public static <T> T getUpdatedValue(T newValue, T existingValue) {
        return newValue != null ? newValue : existingValue;
    }
}
