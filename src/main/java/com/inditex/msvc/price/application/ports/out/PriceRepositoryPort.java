package com.inditex.msvc.price.application.ports.out;


import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;

public interface PriceRepositoryPort {
    Price findByPriceRequest(PriceRequest priceRequest);
}
