package com.inditex.msvc.price.application.ports.in;


import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceSummaryResponse;

public interface PriceUseCases {
    PriceSummaryResponse getPrice(PriceRequest priceRequest);
}
