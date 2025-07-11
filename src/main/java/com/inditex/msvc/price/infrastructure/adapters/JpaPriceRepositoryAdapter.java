package com.inditex.msvc.price.infrastructure.adapters;


import com.inditex.msvc.price.application.ports.out.PriceRepositoryPort;
import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.exception.PriceException;
import com.inditex.msvc.price.infrastructure.adapters.mapper.PriceMapper;
import com.inditex.msvc.price.infrastructure.adapters.repository.SpringDatePriceRepository;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import com.inditex.msvc.price.infrastructure.adapters.utils.GeneralMethods;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JpaPriceRepositoryAdapter implements PriceRepositoryPort {

    private final SpringDatePriceRepository springDatePriceRepository;
    private final PriceMapper priceMapper;

    @Override
    public Price findByPriceRequest(PriceRequest priceRequest) {
        try {
            if (Stream.of(priceRequest.getInputDate(), priceRequest.getProductId(), priceRequest.getBrandId())
                    .anyMatch(Objects::isNull)) {
                throw new PriceException(HttpStatus.BAD_REQUEST, ConstantsUtils.NOT_NULL_VARIABLE);
            }
            var priceEntity = this.springDatePriceRepository.getPriceEntity(
                    priceRequest.getProductId(),
                    priceRequest.getBrandId(),
                    priceRequest.getInputDate());

            return priceEntity.map(this.priceMapper::toPrice)
                    .orElseThrow(() -> new PriceException(HttpStatus.NOT_FOUND,
                            String.format(ConstantsUtils.NOT_FOUND, priceRequest.getProductId())));
        } catch (DateTimeParseException ex) {
            throw new PriceException(HttpStatus.BAD_REQUEST, ConstantsUtils.UNEXPECTED_ERROR);
        }
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
        GeneralMethods.validatePrice(price);
        price.setCreatedDate(LocalDateTime.now());
        return this.priceMapper.toPrice(
                this.springDatePriceRepository.save(this.priceMapper.toPriceEntity(price)));
    }

    @Override
    public Price update(Long id, Price price) {
        GeneralMethods.validatePrice(price);
        return this.springDatePriceRepository.findById(id)
         .map(existingEntity -> {
             var updatedEntity = existingEntity.toBuilder()
             .brandId(price.getBrandId() != null ? price.getBrandId() : existingEntity.getBrandId())
             .startDate(price.getStartDate() != null ? price.getStartDate() : existingEntity.getStartDate())
             .endDate(price.getEndDate() != null ? price.getEndDate() : existingEntity.getEndDate())
             .priceList(price.getPriceList() != null ? price.getPriceList() : existingEntity.getPriceList())
             .productId(price.getProductId() != null ? price.getProductId() : existingEntity.getProductId())
             .priority(price.getPriority() != null ? price.getPriority() : existingEntity.getPriority())
             .priceAmount(price.getPriceAmount() != null ? price.getPriceAmount() : existingEntity.getPriceAmount())
             .currency(price.getCurrency() != null ? price.getCurrency() : existingEntity.getCurrency())
             .lastUpdated(LocalDateTime.now())
             .build();
                return this.priceMapper.toPrice(
                        this.springDatePriceRepository.save(updatedEntity));
            })
            .orElseThrow(() -> new PriceException(HttpStatus.NOT_FOUND,
                    String.format(ConstantsUtils.NOT_FOUND, id)));
    }

    @Override
    public void delete(Long id) {
        if (!this.springDatePriceRepository.existsById(id)) {
            throw new PriceException(HttpStatus.NOT_FOUND, String.format(ConstantsUtils.NOT_FOUND, id));
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
