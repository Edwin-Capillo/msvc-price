package com.inditex.msvc.price.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class PriceRequest implements Serializable {
    @JsonFormat(pattern = ConstantsUtils.FORMAT_DATETIME)
    @NotNull(message = ConstantsUtils.INPUT_DATE_NOT_NULL)
    private LocalDateTime inputDate;
    @NotNull(message = ConstantsUtils.PRODUCT_ID_NOT_NULL)
    private Long productId;
    @NotNull(message = ConstantsUtils.BRAND_ID_NOT_NULL)
    private Long brandId;
}
