package com.inditex.msvc.price.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inditex.msvc.price.infrastructure.adapters.utils.ConstantsUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class PriceRequest implements Serializable {
    @JsonFormat(pattern = ConstantsUtils.FORMAT_DATETIME)
    private LocalDateTime inputDate;
    private Long productId;
    private Long brandId;
}
