package com.inditex.msvc.price.infrastructure.controller;


import com.inditex.msvc.price.application.ports.in.PriceUseCases;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceSummaryResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PriceController {
    private final PriceUseCases priceUseCases;

    @PostMapping("/price")
    public PriceSummaryResponse getPrice(@RequestBody PriceRequest priceRequest) {
        return this.priceUseCases.getPrice(priceRequest);
    }
}