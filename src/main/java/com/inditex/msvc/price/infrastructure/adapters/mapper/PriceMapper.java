package com.inditex.msvc.price.infrastructure.adapters.mapper;

import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.entity.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    Price toPrice(PriceEntity entity);
    PriceEntity toPriceEntity(Price price);

}