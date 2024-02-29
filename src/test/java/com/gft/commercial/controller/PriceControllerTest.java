package com.gft.commercial.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gft.commercial.application.service.PriceService;
import com.gft.commercial.domain.model.Price;
import com.gft.commercial.infrastucture.controller.PriceController;
import com.gft.commercial.infrastucture.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PriceController.class)
@Import(AopAutoConfiguration.class)
@AutoConfigureMockMvc
public class PriceControllerTest {

    public static final Integer BRAND_ID = 1;
    public static final Long PRODUCT_ID = 35455L;
    public static final LocalDateTime DATE = LocalDateTime.of(2020, Month.JUNE, 14, 0, 0, 10);
    public static final Integer PRICE_LIST = 2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @Test
    void getPrice_Success() throws Exception {
        // Given
        when(priceService.getPrice(
                eq(BRAND_ID), eq(PRODUCT_ID), eq(DATE)))
                .thenReturn(Price
                        .builder()
                        .brandId(BRAND_ID)
                        .productId(PRODUCT_ID)
                        .startDate(DATE)
                        .endDate(DATE)
                        .priceList(PRICE_LIST)
                        .build());

        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/35455/prices?date=2020-06-14T00:00:10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(BRAND_ID)))
                .andExpect(jsonPath("$.priceList", is(PRICE_LIST)))
                .andExpect(jsonPath("$.startDate", is(DATE.toString())))
                .andExpect(jsonPath("$.endDate", is(DATE.toString())));
    }

    @Test
    void getPrice_NotFound() throws Exception {
        // Given
        when(priceService.getPrice(
                eq(BRAND_ID), eq(PRODUCT_ID), eq(DATE)))
                .thenThrow(new ResourceNotFoundException("Prices"));

        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/35455/prices?date=2020-06-14T00:00:10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("Prices not found.")));
    }

    @Test
    void getPriceWrongParameter_Error() throws Exception {
        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/invalid/products/35455/prices?date=2020-06-14T00:00:10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.message", is("Invalid parameter: brandId.")));
    }

    @Test
    void getPriceWrongUrl_Error() throws Exception {
        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPriceFatal_Error() throws Exception {
        // Given
        when(priceService.getPrice(
                eq(BRAND_ID), eq(PRODUCT_ID), eq(DATE)))
                .thenThrow(new RuntimeException());
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/35455/prices?date=2020-06-14T00:00:10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.errorCode", is("INTERNAL_ERROR")));
    }
}
