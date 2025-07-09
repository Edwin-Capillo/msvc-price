package com.inditex.msvc.price.application.service;

import com.inditex.msvc.price.application.mapper.PriceResponseMapper;
import com.inditex.msvc.price.application.ports.in.PriceUseCases;
import com.inditex.msvc.price.application.ports.out.PriceRepositoryPort;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceSummaryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PriceService implements PriceUseCases {
    private final PriceRepositoryPort priceRepositoryPort;
    private final PriceResponseMapper priceResponseMapper;

    @Override
    public PriceSummaryResponse getPrice(PriceRequest priceRequest) {
        return this.priceResponseMapper.toPriceSummaryResponse(
                this.priceRepositoryPort.findByPriceRequest(priceRequest));
    }
}
