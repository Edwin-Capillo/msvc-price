package com.inditex.msvc.price.application.ports.in;


import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailResponse;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceSummaryResponse;

import java.util.List;

public interface PriceUseCases {
    PriceSummaryResponse getPrice(PriceRequest priceRequest);
    PriceDetailResponse getById(Long id);
    PriceDetailResponse createPrice(PriceDetailRequest priceDetailRequest);
    PriceDetailResponse updatePrice(Long id, PriceDetailRequest priceDetailRequest);
    void deletePrice(Long id);
    List<PriceDetailResponse> getAll();
}
