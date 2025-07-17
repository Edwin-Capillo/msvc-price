package com.inditex.msvc.price.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PriceDetailRequest {
    @NotNull(message = ConstantsUtils.BRAND_ID_NOT_NULL)
    private Long brandId;
    @JsonFormat(pattern = ConstantsUtils.FORMAT_DATETIME)
    @NotNull(message = ConstantsUtils.START_DATE_NOT_NULL)
    private LocalDateTime startDate;
    @JsonFormat(pattern = ConstantsUtils.FORMAT_DATETIME)
    @NotNull(message = ConstantsUtils.END_DATE_NOT_NULL)
    private LocalDateTime endDate;
    @NotNull(message = ConstantsUtils.PRICE_LIST_NOT_NULL)
    private Integer priceList;
    @NotNull(message = ConstantsUtils.PRODUCT_ID_NOT_NULL)
    private Long productId;
    @NotNull(message = ConstantsUtils.PRIORITY_NOT_NULL)
    private Integer priority;
    @NotNull(message = ConstantsUtils.PRICE_AMOUNT_NOT_NULL)
    @Positive(message = ConstantsUtils.PRICE_AMOUNT_POSITIVE)
    private Double priceAmount;
    @NotNull(message = ConstantsUtils.CURRENCY_NOT_NULL)
    private String currency;
}
