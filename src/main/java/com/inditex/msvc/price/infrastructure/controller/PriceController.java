package com.inditex.msvc.price.infrastructure.controller;

import com.inditex.msvc.price.application.ports.in.PriceUseCases;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailResponse;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceSummaryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class PriceController {
    private final PriceUseCases priceUseCases;

    @PostMapping("/price")
    public ResponseEntity<PriceSummaryResponse> getPrice(@RequestBody @Valid PriceRequest priceRequest) {
        return ResponseEntity.ok(this.priceUseCases.getPrice(priceRequest));
    }

    @GetMapping("/price/{id}")
    public ResponseEntity<PriceDetailResponse> getById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(this.priceUseCases.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PriceDetailResponse> createPrice(@RequestBody @Valid PriceDetailRequest priceDetailRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.priceUseCases.createPrice(priceDetailRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PriceDetailResponse> updatePrice(@PathVariable @Positive Long id,
            @Valid @RequestBody PriceDetailRequest priceDetailRequest) {
        return ResponseEntity.ok(this.priceUseCases.updatePrice(id, priceDetailRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable @Positive Long id) {
        this.priceUseCases.deletePrice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/prices")
    public ResponseEntity<List<PriceDetailResponse>> getAll() {
        return ResponseEntity.ok(priceUseCases.getAll());
    }

}