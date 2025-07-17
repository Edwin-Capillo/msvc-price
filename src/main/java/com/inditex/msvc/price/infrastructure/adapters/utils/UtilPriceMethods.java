package com.inditex.msvc.price.infrastructure.adapters.utils;

import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.entity.PriceEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class UtilPriceMethods {
    private UtilPriceMethods() {
    }
    public static PriceEntity buildPrice(Price price, PriceEntity existingEntity) {
        return existingEntity.toBuilder()
                .brandId(price.getBrandId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .priceList(price.getPriceList())
                .productId(price.getProductId())
                .priority(price.getPriority())
                .priceAmount(price.getPriceAmount())
                .currency(price.getCurrency())
                .lastUpdated(LocalDateTime.now())
                .build();
    }
}
