package com.inditex.msvc.price.infrastructure.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    // en CsvSource se especifican que los valores de entrada son las primeras 3 columnas y
    // las siguientes columnas representan los valores esperados
    @CsvSource({
            "2020-06-14-10:00:00, 35455, 1, 35455, 1, 1, 2020-06-14-00:00:00, 2020-12-31-23:59:59, 35.50, EUR",
            "2020-06-14-16:00:00, 35455, 1, 35455, 1, 2, 2020-06-14-15:00:00, 2020-06-14-18:30:00, 25.45, EUR",
            "2020-06-14-21:00:00, 35455, 1, 35455, 1, 1, 2020-06-14-00:00:00, 2020-12-31-23:59:59, 35.50, EUR",
            "2020-06-15-10:00:00, 35455, 1, 35455, 1, 3, 2020-06-15-00:00:00, 2020-06-15-11:00:00, 30.50, EUR",
            "2020-06-16-21:00:00, 35455, 1, 35455, 1, 4, 2020-06-15-16:00:00, 2020-12-31-23:59:59, 38.95, EUR"
    })
    void testGetPrice(String inputDate, long productId, long brandId, long expectedProductId, long expectedBrandId,
                      int expectedPriceList, String expectedStartDate, String expectedEndDate, double expectedPrice,
                      String expectedCurrency) throws Exception {
        String requestBody = String.format("""
                {
                    "inputDate": "%s",
                    "productId": %d,
                    "brandId": %d
                }
                """, inputDate, productId, brandId);

        mockMvc.perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(expectedProductId))
                .andExpect(jsonPath("$.brandId").value(expectedBrandId))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDate))
                .andExpect(jsonPath("$.endDate").value(expectedEndDate))
                .andExpect(jsonPath("$.priceAmount").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(expectedCurrency));
    }
}