package com.inditex.msvc.price.infrastructure.adapters;

import com.inditex.msvc.price.domain.model.Price;
import com.inditex.msvc.price.infrastructure.adapters.entity.PriceEntity;
import com.inditex.msvc.price.infrastructure.adapters.exception.PriceException;
import com.inditex.msvc.price.infrastructure.adapters.mapper.PriceMapper;
import com.inditex.msvc.price.infrastructure.adapters.repository.SpringDatePriceRepository;
import com.inditex.msvc.price.infrastructure.adapters.utils.GeneralMethods;
import com.inditex.msvc.price.infrastructure.controller.dto.PriceRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class JpaPriceRepositoryAdapterTest {

    @Mock
    private SpringDatePriceRepository springDatePriceRepository;

    @Mock
    private PriceMapper priceMapper;
    @InjectMocks
    private JpaPriceRepositoryAdapter jpaPriceRepositoryAdapter;

    @Test
    void testFindByPriceRequest_Success() {
        PriceRequest request = new PriceRequest();
        request.setInputDate(LocalDateTime.now());
        request.setProductId(35455L);
        request.setBrandId(1L);

        PriceEntity mockEntity = new PriceEntity();
        Price mockPrice = new Price();
        Mockito.when(springDatePriceRepository.getPriceEntity(eq(35455L), eq(1L), any(LocalDateTime.class)))
                .thenReturn(Optional.of(mockEntity));
        Mockito.when(priceMapper.toPrice(mockEntity)).thenReturn(mockPrice);

        Price result = jpaPriceRepositoryAdapter.findByPriceRequest(request);

        assertNotNull(result);
        Mockito.verify(springDatePriceRepository).getPriceEntity(eq(35455L), eq(1L), any(LocalDateTime.class));
        Mockito.verify(priceMapper).toPrice(mockEntity);
    }

    @Test
    void testFindByPriceRequest_NotFound() {
        PriceRequest request = new PriceRequest();
        request.setInputDate(LocalDateTime.now());
        request.setProductId(35455L);
        request.setBrandId(1L);

        Mockito.when(springDatePriceRepository.getPriceEntity(eq(35455L), eq(1L), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        PriceException exception = assertThrows(PriceException.class, () -> jpaPriceRepositoryAdapter.findByPriceRequest(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testGetById_Success() {
        PriceEntity mockEntity = new PriceEntity();
        Price mockPrice = new Price();
        Mockito.when(springDatePriceRepository.findById(1L)).thenReturn(Optional.of(mockEntity));
        Mockito.when(priceMapper.toPrice(mockEntity)).thenReturn(mockPrice);

        Price result = jpaPriceRepositoryAdapter.getById(1L);

        assertNotNull(result);
        Mockito.verify(springDatePriceRepository).findById(1L);
        Mockito.verify(priceMapper).toPrice(mockEntity);
    }

    @Test
    void testGetById_NotFound() {
        Mockito.when(springDatePriceRepository.findById(1L)).thenReturn(Optional.empty());

        PriceException exception = assertThrows(PriceException.class, () -> jpaPriceRepositoryAdapter.getById(1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testSave_Success() {
        Price mockPrice = new Price();
        PriceEntity mockEntity = new PriceEntity();

        try (var mockedStatic = Mockito.mockStatic(GeneralMethods.class)) {
            mockedStatic.when(() -> GeneralMethods.validatePrice(mockPrice)).thenAnswer(invocation -> null); // Mock validation
            Mockito.when(priceMapper.toPriceEntity(mockPrice)).thenReturn(mockEntity);
            Mockito.when(springDatePriceRepository.save(mockEntity)).thenReturn(mockEntity);
            Mockito.when(priceMapper.toPrice(mockEntity)).thenReturn(mockPrice);

            Price result = jpaPriceRepositoryAdapter.save(mockPrice);

            assertNotNull(result);
            Mockito.verify(priceMapper).toPriceEntity(mockPrice);
            Mockito.verify(springDatePriceRepository).save(mockEntity);
            Mockito.verify(priceMapper).toPrice(mockEntity);
        }
    }

    @Test
    void testUpdate_Success() {
        Price mockPrice = new Price();
        PriceEntity existingEntity = new PriceEntity();
        PriceEntity updatedEntity = existingEntity.toBuilder().build();

        try (var mockedStatic = Mockito.mockStatic(GeneralMethods.class)) {
            mockedStatic.when(() -> GeneralMethods.validatePrice(mockPrice)).thenAnswer(invocation -> null);
            Mockito.when(springDatePriceRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
            Mockito.when(springDatePriceRepository.save(Mockito.any(PriceEntity.class))).thenReturn(updatedEntity);
            Mockito.when(priceMapper.toPrice(updatedEntity)).thenReturn(mockPrice);

            Price result = jpaPriceRepositoryAdapter.update(1L, mockPrice);

            assertNotNull(result);
            assertEquals(mockPrice.getBrandId(), result.getBrandId());
            assertEquals(mockPrice.getPriceAmount(), result.getPriceAmount());
            Mockito.verify(springDatePriceRepository).findById(1L);
            Mockito.verify(springDatePriceRepository).save(Mockito.any(PriceEntity.class));
            Mockito.verify(priceMapper).toPrice(updatedEntity);
        }
    }

    @Test
    void testUpdate_NotFound() {
        Price mockPrice = new Price();

        try (var mockedStatic = Mockito.mockStatic(GeneralMethods.class)) {
            mockedStatic.when(() -> GeneralMethods.validatePrice(mockPrice)).thenAnswer(invocation -> null); // Mock validation
            Mockito.when(springDatePriceRepository.findById(1L)).thenReturn(Optional.empty());

            PriceException exception = assertThrows(PriceException.class, () -> jpaPriceRepositoryAdapter.update(1L, mockPrice));
            assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
        }
    }

    @Test
    void testDelete_Success() {
        Mockito.when(springDatePriceRepository.existsById(1L)).thenReturn(true);

        jpaPriceRepositoryAdapter.delete(1L);

        Mockito.verify(springDatePriceRepository).existsById(1L);
        Mockito.verify(springDatePriceRepository).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        Mockito.when(springDatePriceRepository.existsById(1L)).thenReturn(false);

        PriceException exception = assertThrows(PriceException.class, () -> jpaPriceRepositoryAdapter.delete(1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testGetAll_Success() {
        PriceEntity mockEntity = new PriceEntity();
        Price mockPrice = new Price();
        Mockito.when(springDatePriceRepository.findAll()).thenReturn(Collections.singletonList(mockEntity));
        Mockito.when(priceMapper.toPrice(mockEntity)).thenReturn(mockPrice);

        var result = jpaPriceRepositoryAdapter.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        Mockito.verify(springDatePriceRepository).findAll();
        Mockito.verify(priceMapper).toPrice(mockEntity);
    }
}