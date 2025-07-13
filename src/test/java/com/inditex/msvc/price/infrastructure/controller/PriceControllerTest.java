package com.inditex.msvc.price.infrastructure.controller;

import com.inditex.msvc.price.application.ports.in.PriceUseCases;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PriceUseCases priceUseCases;

    @InjectMocks
    private PriceController priceController;

    private void setupMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
    }

    private void mockPriceResponse(PriceSummaryResponse response) {
        Mockito.when(priceUseCases.getPrice(any(PriceRequest.class))).thenReturn(response);
    }

    @Test
    void testGetPrice() throws Exception {
        setupMockMvc();

        PriceSummaryResponse response = new PriceSummaryResponse();
        response.setProductId(35455L);
        response.setBrandId(1L);
        response.setPriceList(1);
        response.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        response.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        response.setPriceAmount(35.50);
        response.setCurrency("EUR");

        Mockito.when(priceUseCases.getPrice(any(PriceRequest.class))).thenReturn(response);

        String requestBody = """
                {
                    "inputDate": "2020-06-14-10:00:00",
                    "productId": 35455,
                    "brandId": 1
                }
                """;

        mockMvc.perform(post("/api/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455L))
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14-00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31-23:59:59"))
                .andExpect(jsonPath("$.priceAmount").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void testGetById() throws Exception {
        setupMockMvc();

        PriceDetailResponse response = new PriceDetailResponse();
        response.setBrandId(1L);
        response.setStartDate(LocalDateTime.parse("2023-10-05T15:30:45"));
        response.setEndDate(LocalDateTime.parse("2023-10-05T16:30:45"));
        response.setPriceList(2);
        response.setProductId(35455L);
        response.setPriority(1);
        response.setPriceAmount(100.0);
        response.setCurrency("EUR");

        Mockito.when(priceUseCases.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/price/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.startDate").value("2023-10-05-15:30:45"))
                .andExpect(jsonPath("$.endDate").value("2023-10-05-16:30:45"))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.priority").value(1))
                .andExpect(jsonPath("$.priceAmount").value(100.0))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void testCreatePrice() throws Exception {
        setupMockMvc();

        PriceDetailResponse response = new PriceDetailResponse();
        response.setBrandId(1L);
        response.setStartDate(LocalDateTime.parse("2023-10-05T15:30:45"));
        response.setEndDate(LocalDateTime.parse("2023-10-05T16:30:45"));
        response.setPriceList(2);
        response.setProductId(35455L);
        response.setPriority(1);
        response.setPriceAmount(100.0);
        response.setCurrency("EUR");

        Mockito.when(priceUseCases.createPrice(any(PriceDetailRequest.class))).thenReturn(response);

        String requestBody = """
                {
                    "brandId": 1,
                    "startDate": "2023-10-05-15:30:45",
                    "endDate": "2023-10-05-16:30:45",
                    "priceList": 2,
                    "productId": 35455,
                    "priority": 1,
                    "priceAmount": 100.0,
                    "currency": "EUR"
                }
                """;

        mockMvc.perform(post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.startDate").value("2023-10-05-15:30:45"))
                .andExpect(jsonPath("$.endDate").value("2023-10-05-16:30:45"))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.productId").value(35455L))
                .andExpect(jsonPath("$.priority").value(1))
                .andExpect(jsonPath("$.priceAmount").value(100.0))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void testUpdatePrice() throws Exception {
        setupMockMvc();

        PriceDetailResponse response = new PriceDetailResponse();
        response.setBrandId(1L);
        response.setStartDate(LocalDateTime.parse("2023-10-05T15:30:45"));
        response.setEndDate(LocalDateTime.parse("2023-10-05T16:30:45"));
        response.setPriceList(2);
        response.setProductId(35455L);
        response.setPriority(1);
        response.setPriceAmount(120.0);
        response.setCurrency("USD");

        Mockito.when(priceUseCases.updatePrice(eq(1L), any(PriceDetailRequest.class))).thenReturn(response);

        String requestBody = """
                {
                    "brandId": 1,
                    "startDate": "2023-10-05-15:30:45",
                    "endDate": "2023-10-05-16:30:45",
                    "priceList": 2,
                    "productId": 35455,
                    "priority": 1,
                    "priceAmount": 120.0,
                    "currency": "USD"
                }
                """;

        mockMvc.perform(put("/api/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.startDate").value("2023-10-05-15:30:45"))
                .andExpect(jsonPath("$.endDate").value("2023-10-05-16:30:45"))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.productId").value(35455L))
                .andExpect(jsonPath("$.priority").value(1))
                .andExpect(jsonPath("$.priceAmount").value(120.0))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void testDeletePrice() throws Exception {
        setupMockMvc();

        Mockito.doNothing().when(priceUseCases).deletePrice(1L);

        mockMvc.perform(delete("/api/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAll() throws Exception {
        setupMockMvc();

        PriceDetailResponse response = new PriceDetailResponse();
        response.setBrandId(1L);
        response.setStartDate(LocalDateTime.parse("2023-10-05T15:30:45"));
        response.setEndDate(LocalDateTime.parse("2023-10-05T16:30:45"));
        response.setPriceList(2);
        response.setProductId(35455L);
        response.setPriority(1);
        response.setPriceAmount(100.0);
        response.setCurrency("EUR");

        List<PriceDetailResponse> responseList = Collections.singletonList(response);

        Mockito.when(priceUseCases.getAll()).thenReturn(responseList);

        mockMvc.perform(get("/api/prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brandId").value(1L))
                .andExpect(jsonPath("$[0].startDate").value("2023-10-05-15:30:45"))
                .andExpect(jsonPath("$[0].endDate").value("2023-10-05-16:30:45"))
                .andExpect(jsonPath("$[0].priceList").value(2))
                .andExpect(jsonPath("$[0].productId").value(35455L))
                .andExpect(jsonPath("$[0].priority").value(1))
                .andExpect(jsonPath("$[0].priceAmount").value(100.0))
                .andExpect(jsonPath("$[0].currency").value("EUR"));
    }

    /*
     * Desarrollar unos test al endpoint rest que validen las siguientes peticiones
     * al servicio con los datos del ejemplo:
     * - Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1
     * (ZARA)
     * - Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1
     * (ZARA)
     * - Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1
     * (ZARA)
     * - Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1
     * (ZARA)
     * - Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1
     * (ZARA)
     */
    @Test
    void Test_1() throws Exception {
        setupMockMvc();

        PriceSummaryResponse response = new PriceSummaryResponse();
        response.setProductId(35455L);
        response.setBrandId(1L);
        response.setPriceList(1);
        response.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        response.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        response.setPriceAmount(35.50);
        response.setCurrency("EUR");

        mockPriceResponse(response);

        String requestBody = """
                {
                    "productId": 35455,
                    "brandId": 1,
                    "inputDate": "2020-06-14-10:00:00"
                }
                """;

        mockMvc.perform(post("/api/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455L))
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14-00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31-23:59:59"))
                .andExpect(jsonPath("$.priceAmount").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void Test_2() throws Exception {
        setupMockMvc();

        PriceSummaryResponse response = new PriceSummaryResponse();
        response.setProductId(35455L);
        response.setBrandId(1L);
        response.setPriceList(2);
        response.setStartDate(LocalDateTime.parse("2020-06-14T15:00:00"));
        response.setEndDate(LocalDateTime.parse("2020-06-14T18:30:00"));
        response.setPriceAmount(25.45);
        response.setCurrency("EUR");

        mockPriceResponse(response);

        String requestBody = """
                {
                    "productId": 35455,
                    "brandId": 1,
                    "inputDate": "2020-06-14-16:00:00"
                }
                """;

        mockMvc.perform(post("/api/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455L))
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.startDate").value("2020-06-14-15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14-18:30:00"))
                .andExpect(jsonPath("$.priceAmount").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void Test_3() throws Exception {
        setupMockMvc();

        PriceSummaryResponse response = new PriceSummaryResponse();
        response.setProductId(35455L);
        response.setBrandId(1L);
        response.setPriceList(1);
        response.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        response.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        response.setPriceAmount(35.50);
        response.setCurrency("EUR");

        mockPriceResponse(response);

        String requestBody = """
                {
                    "productId": 35455,
                    "brandId": 1,
                    "inputDate": "2020-06-14-21:00:00"
                }
                """;

        mockMvc.perform(post("/api/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455L))
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14-00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31-23:59:59"))
                .andExpect(jsonPath("$.priceAmount").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void Test_4() throws Exception {
        setupMockMvc();

        PriceSummaryResponse response = new PriceSummaryResponse();
        response.setProductId(35455L);
        response.setBrandId(1L);
        response.setPriceList(3);
        response.setStartDate(LocalDateTime.parse("2020-06-15T00:00:00"));
        response.setEndDate(LocalDateTime.parse("2020-06-15T11:00:00"));
        response.setPriceAmount(30.50);
        response.setCurrency("EUR");

        mockPriceResponse(response);

        String requestBody = """
                {
                    "productId": 35455,
                    "brandId": 1,
                    "inputDate": "2020-06-15-10:00:00"
                }
                """;

        mockMvc.perform(post("/api/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455L))
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.startDate").value("2020-06-15-00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15-11:00:00"))
                .andExpect(jsonPath("$.priceAmount").value(30.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void Test_5() throws Exception {
        setupMockMvc();

        PriceSummaryResponse response = new PriceSummaryResponse();
        response.setProductId(35455L);
        response.setBrandId(1L);
        response.setPriceList(4);
        response.setStartDate(LocalDateTime.parse("2020-06-15T16:00:00"));
        response.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        response.setPriceAmount(38.95);
        response.setCurrency("EUR");

        mockPriceResponse(response);

        String requestBody = """
                {
                    "productId": 35455,
                    "brandId": 1,
                    "inputDate": "2020-06-16-21:00:00"
                }
                """;

        mockMvc.perform(post("/api/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455L))
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.startDate").value("2020-06-15-16:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31-23:59:59"))
                .andExpect(jsonPath("$.priceAmount").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

}