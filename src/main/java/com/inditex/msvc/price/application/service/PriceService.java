package com.inditex.msvc.price.application.service;

import com.inditex.msvc.price.application.mapper.PriceResponseMapper;
import com.inditex.msvc.price.application.ports.in.PriceUseCases;
import com.inditex.msvc.price.application.ports.out.PriceRepositoryPort;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailResponse;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PriceService implements PriceUseCases {
    private final PriceRepositoryPort priceRepositoryPort;
    private final PriceResponseMapper priceResponseMapper;

    @Override
    public PriceSummaryResponse getPrice(PriceRequest priceRequest) {
        return this.priceResponseMapper.toPriceSummaryResponse(
                this.priceRepositoryPort.findByPriceRequest(priceRequest));
    }

    @Override
    public PriceDetailResponse getById(Long id) {
        return this.priceResponseMapper.toPriceDetailResponse(
                this.priceRepositoryPort.getById(id));
    }

    @Override
    public PriceDetailResponse createPrice(PriceDetailRequest priceDetailRequest) {
        return this.priceResponseMapper.toPriceDetailResponse(
                this.priceRepositoryPort.save(
                        this.priceResponseMapper.toPrice(priceDetailRequest)));
    }

    @Override
    public PriceDetailResponse updatePrice(Long id, PriceDetailRequest priceDetailRequest) {
        return this.priceResponseMapper.toPriceDetailResponse(
                this.priceRepositoryPort.update(
                        id,
                        this.priceResponseMapper.toPrice(priceDetailRequest)));
    }

    @Override
    public void deletePrice(Long id) {
        this.priceRepositoryPort.delete(id);
    }

    @Override
    public List<PriceDetailResponse> getAll() {
        return this.priceResponseMapper.toListPriceDetailResponse(
                this.priceRepositoryPort.getAll());
    }
}
