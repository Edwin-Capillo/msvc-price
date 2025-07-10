package com.inditex.msvc.price.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PriceDetailRequest {
    private Long brandId;
    @JsonFormat(pattern = ConstantsUtils.FORMAT_DATETIME)
    private LocalDateTime startDate;
    @JsonFormat(pattern = ConstantsUtils.FORMAT_DATETIME)
    private LocalDateTime endDate;
    private Integer priceList;
    private Long productId;
    private Integer priority;
    private Double priceAmount;
    private String currency;
}
