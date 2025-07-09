package com.inditex.msvc.price.application.mapper;


import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "brandId", target = "brandId")
    @Mapping(source = "priceList", target = "priceList")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "priceAmount", target = "priceAmount")
    @Mapping(source = "currency", target = "currency")
    PriceSummaryResponse toPriceSummaryResponse(Price price);

}
