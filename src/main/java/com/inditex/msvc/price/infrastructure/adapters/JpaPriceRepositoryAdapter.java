package com.inditex.msvc.price.infrastructure.adapters;


import com.inditex.msvc.price.application.ports.out.PriceRepositoryPort;
import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.exception.PriceException;
import com.inditex.msvc.price.infrastructure.adapters.mapper.PriceMapper;
import com.inditex.msvc.price.infrastructure.adapters.repository.SpringDatePriceRepository;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import com.inditex.msvc.price.infrastructure.adapters.utils.UtilPriceMethods;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaPriceRepositoryAdapter implements PriceRepositoryPort {

    private final SpringDatePriceRepository springDatePriceRepository;
    private final PriceMapper priceMapper;

    @Override
    public Price findByPriceRequest(PriceRequest priceRequest) {
        var priceEntity = this.springDatePriceRepository.getPriceEntity(
                priceRequest.getProductId(),
                priceRequest.getBrandId(),
                priceRequest.getInputDate());

        return priceEntity.map(this.priceMapper::toPrice)
                .orElseThrow(() -> new PriceException(HttpStatus.NOT_FOUND,
                        String.format(ConstantsUtils.NOT_FOUND, priceRequest.getProductId())));

    }

    @Override
    public Price getById(Long id) {
        return this.springDatePriceRepository.findById(id)
                .map(this.priceMapper::toPrice)
                .orElseThrow(() -> new PriceException(HttpStatus.NOT_FOUND,
                        String.format(ConstantsUtils.NOT_FOUND, id)));
    }

    @Override
    public Price save(Price price) {
        price.setCreatedDate(LocalDateTime.now());
        return this.priceMapper.toPrice(
                this.springDatePriceRepository.save(this.priceMapper.toPriceEntity(price)));
    }

    @Override
    public Price update(Long id, Price price) {
        return this.springDatePriceRepository.findById(id)
                .map(existingEntity -> {
                    return this.priceMapper.toPrice(
                            this.springDatePriceRepository.save(
                                    UtilPriceMethods.buildPrice(price, existingEntity)));
                })
                .orElseThrow(() -> new PriceException(HttpStatus.NOT_FOUND,
                        String.format(ConstantsUtils.NOT_FOUND, id)));
    }

    @Override
    public void delete(Long id) {
        if (!this.springDatePriceRepository.existsById(id)) {
            throw new PriceException(HttpStatus.NOT_FOUND,
                    String.format(ConstantsUtils.NOT_FOUND, id));
        }
        this.springDatePriceRepository.deleteById(id);
    }

    @Override
    public List<Price> getAll() {
        return springDatePriceRepository.findAll()
                .stream()
                .map(priceMapper::toPrice)
                .toList();
    }
}
