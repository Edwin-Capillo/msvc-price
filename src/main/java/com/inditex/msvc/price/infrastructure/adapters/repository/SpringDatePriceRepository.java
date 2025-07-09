package com.inditex.msvc.price.infrastructure.adapters.repository;

import com.inditex.msvc.price.infrastructure.adapters.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDatePriceRepository extends JpaRepository <PriceEntity, Long> {
    @Query(value = """
        SELECT * FROM PRICE p
        WHERE p.PRODUCT_ID = :productId
        AND p.BRAND_ID = :brandId
        AND :inputDate BETWEEN p.START_DATE AND p.END_DATE
        ORDER BY p.PRIORITY DESC
        LIMIT 1
        """, nativeQuery = true)
    Optional<PriceEntity> getPriceEntity(@Param("productId") Long productId,
                                         @Param("brandId") Long brandId,
                                         @Param("inputDate") java.time.LocalDateTime inputDate);
}
