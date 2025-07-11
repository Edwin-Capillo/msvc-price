package com.inditex.msvc.price.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Price {
    private Long id;
    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Long productId;
    private Integer priority;
    private Double priceAmount;
    private String currency;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
    private String createdBy;
}
