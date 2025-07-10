package com.inditex.msvc.price.application.ports.out;


import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;

import java.util.List;

public interface PriceRepositoryPort {
    Price findByPriceRequest(PriceRequest priceRequest);
    Price getById(Long id);
    Price save(Price price);
    Price update(Long id, Price price);
    void delete(Long id);
    List<Price> getAll();
}
