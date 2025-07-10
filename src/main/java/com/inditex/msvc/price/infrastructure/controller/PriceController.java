package com.inditex.msvc.price.infrastructure.controller;


import com.inditex.msvc.price.application.ports.in.PriceUseCases;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailResponse;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PriceController {
    private final PriceUseCases priceUseCases;

    @PostMapping("/price")
    public PriceSummaryResponse getPrice(@RequestBody PriceRequest priceRequest) {
        return this.priceUseCases.getPrice(priceRequest);
    }

    @GetMapping("/price/{id}")
    public PriceDetailResponse getById(@PathVariable Long id) {
        return this.priceUseCases.getById(id);
    }

    @PostMapping("/create")
    public PriceDetailResponse createPrice(@RequestBody PriceDetailRequest priceDetailRequest) {
        return this.priceUseCases.createPrice(priceDetailRequest);
    }

    @PutMapping("/update/{id}")
    public PriceDetailResponse updatePrice(@PathVariable Long id, @RequestBody PriceDetailRequest priceDetailRequest) {
        return this.priceUseCases.updatePrice(id, priceDetailRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePrice(@PathVariable Long id) {
        this.priceUseCases.deletePrice(id);
    }

    @GetMapping("/prices")
    public List<PriceDetailResponse> getAll() {
        return priceUseCases.getAll();
    }

}