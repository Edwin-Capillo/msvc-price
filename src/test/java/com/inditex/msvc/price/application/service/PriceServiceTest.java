package com.inditex.msvc.price.application.service;

import com.inditex.msvc.price.application.mapper.PriceResponseMapper;
import com.inditex.msvc.price.application.ports.out.PriceRepositoryPort;
import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceDetailResponse;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceSummaryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @Mock
    private PriceResponseMapper priceResponseMapper;

    @InjectMocks
    private PriceService priceService;

    @Test
    void testGetPrice() {
        PriceRequest request = new PriceRequest();
        request.setInputDate(LocalDateTime.now());
        request.setProductId(35455L);
        request.setBrandId(1L);

        Price mockPrice = new Price();
        PriceSummaryResponse mockResponse = new PriceSummaryResponse();

        Mockito.when(priceRepositoryPort.findByPriceRequest(request)).thenReturn(mockPrice);
        Mockito.when(priceResponseMapper.toPriceSummaryResponse(mockPrice)).thenReturn(mockResponse);

        PriceSummaryResponse result = priceService.getPrice(request);

        assertNotNull(result);
        Mockito.verify(priceRepositoryPort).findByPriceRequest(request);
        Mockito.verify(priceResponseMapper).toPriceSummaryResponse(mockPrice);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Price mockPrice = new Price();
        PriceDetailResponse mockResponse = new PriceDetailResponse();

        Mockito.when(priceRepositoryPort.getById(id)).thenReturn(mockPrice);
        Mockito.when(priceResponseMapper.toPriceDetailResponse(mockPrice)).thenReturn(mockResponse);

        PriceDetailResponse result = priceService.getById(id);

        assertNotNull(result);
        Mockito.verify(priceRepositoryPort).getById(id);
        Mockito.verify(priceResponseMapper).toPriceDetailResponse(mockPrice);
    }

    @Test
    void testCreatePrice() {
        PriceDetailRequest request = new PriceDetailRequest();
        Price mockPrice = new Price();
        PriceDetailResponse mockResponse = new PriceDetailResponse();

        Mockito.when(priceResponseMapper.toPrice(request)).thenReturn(mockPrice);
        Mockito.when(priceRepositoryPort.save(mockPrice)).thenReturn(mockPrice);
        Mockito.when(priceResponseMapper.toPriceDetailResponse(mockPrice)).thenReturn(mockResponse);

        PriceDetailResponse result = priceService.createPrice(request);

        assertNotNull(result);
        Mockito.verify(priceResponseMapper).toPrice(request);
        Mockito.verify(priceRepositoryPort).save(mockPrice);
        Mockito.verify(priceResponseMapper).toPriceDetailResponse(mockPrice);
    }

    @Test
    void testUpdatePrice() {
        Long id = 1L;
        PriceDetailRequest request = new PriceDetailRequest();
        Price mockPrice = new Price();
        PriceDetailResponse mockResponse = new PriceDetailResponse();

        Mockito.when(priceResponseMapper.toPrice(request)).thenReturn(mockPrice);
        Mockito.when(priceRepositoryPort.update(id, mockPrice)).thenReturn(mockPrice);
        Mockito.when(priceResponseMapper.toPriceDetailResponse(mockPrice)).thenReturn(mockResponse);

        PriceDetailResponse result = priceService.updatePrice(id, request);

        assertNotNull(result);
        Mockito.verify(priceResponseMapper).toPrice(request);
        Mockito.verify(priceRepositoryPort).update(id, mockPrice);
        Mockito.verify(priceResponseMapper).toPriceDetailResponse(mockPrice);
    }

    @Test
    void testDeletePrice() {
        Long id = 1L;

        Mockito.doNothing().when(priceRepositoryPort).delete(id);

        assertDoesNotThrow(() -> priceService.deletePrice(id));
        Mockito.verify(priceRepositoryPort).delete(id);
    }

    @Test
    void testGetAll() {
        Price mockPrice = new Price();
        PriceDetailResponse mockResponse = new PriceDetailResponse();

        Mockito.when(priceRepositoryPort.getAll()).thenReturn(Collections.singletonList(mockPrice));
        Mockito.when(priceResponseMapper.toListPriceDetailResponse(Collections.singletonList(mockPrice)))
                .thenReturn(Collections.singletonList(mockResponse));

        List<PriceDetailResponse> result = priceService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        Mockito.verify(priceRepositoryPort).getAll();
        Mockito.verify(priceResponseMapper).toListPriceDetailResponse(Collections.singletonList(mockPrice));
    }
}