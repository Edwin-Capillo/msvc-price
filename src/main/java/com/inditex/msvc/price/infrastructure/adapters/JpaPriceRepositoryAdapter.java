package com.inditex.msvc.price.infrastructure.adapters;


import com.inditex.msvc.price.application.ports.out.PriceRepositoryPort;
import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.exception.PriceException;
import com.inditex.msvc.price.infrastructure.adapters.mapper.PriceMapper;
import com.inditex.msvc.price.infrastructure.adapters.repository.SpringDatePriceRepository;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
public class JpaPriceRepositoryAdapter implements PriceRepositoryPort {

    private final SpringDatePriceRepository springDatePriceRepository;
    private final PriceMapper priceMapper;

    @Override
    public Price findByPriceRequest(PriceRequest priceRequest) {
        try {
            if (Stream.of(priceRequest.getInputDate(), priceRequest.getProductId(), priceRequest.getBrandId())
                    .anyMatch(Objects::isNull)) {
                throw new PriceException(HttpStatus.BAD_REQUEST, ConstantsUtils.NOTNULLVARIABLE);
            }

            var priceEntity = this.springDatePriceRepository.getPriceEntity(
                    priceRequest.getProductId(),
                    priceRequest.getBrandId(),
                    priceRequest.getInputDate());

            return priceEntity.map(this.priceMapper::toPrice)
                    .orElseThrow(() -> new PriceException(HttpStatus.NOT_FOUND,
                            String.format(ConstantsUtils.NOTFOUND, priceRequest.getProductId())));

        } catch (DateTimeParseException ex) {
            throw new PriceException(HttpStatus.BAD_REQUEST, ConstantsUtils.UNEXPECTEDERROR);
        }
    }
}
