package com.inditex.msvc.price.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class PriceDetailRequest {
    @NotNull
    private Long brandId;
    @JsonFormat(pattern = ConstantsUtils.FORMAT_DATETIME)
    @NotNull
    private LocalDateTime startDate;
    @JsonFormat(pattern = ConstantsUtils.FORMAT_DATETIME)
    @NotNull
    private LocalDateTime endDate;
    @NotNull
    private Integer priceList;
    @NotNull
    private Long productId;
    @NotNull
    private Integer priority;
    @NotNull
    private Double priceAmount;
    @NotNull
    private String currency;
}
