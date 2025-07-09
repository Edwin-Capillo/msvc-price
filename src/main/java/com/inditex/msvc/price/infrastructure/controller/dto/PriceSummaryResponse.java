package com.inditex.msvc.price.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PriceSummaryResponse {
    private Long productId;
    private Long brandId;
    private Integer priceList;
    @JsonFormat(pattern = ConstantsUtils.FORMATDATETIME)
    private LocalDateTime startDate;
    @JsonFormat(pattern = ConstantsUtils.FORMATDATETIME)
    private LocalDateTime endDate;
    private Double priceAmount;
    private String currency;
}
