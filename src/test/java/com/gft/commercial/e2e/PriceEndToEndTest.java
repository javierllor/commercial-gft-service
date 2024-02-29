package com.gft.commercial.e2e;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
@Sql(
        scripts = {"/data/prices_schema.sql", "/data/insert_prices.sql"},
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/data/delete_prices.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PriceEndToEndTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
    public void test1Success() throws Exception {

        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/35455/prices?date=2020-06-14T10:00:00")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.startDate", is("2020-06-14T00:00:00")))
                .andExpect(jsonPath("$.endDate", is("2020-12-31T23:59:59")))
                .andExpect(jsonPath("$.finalPrice", is("35.5 EUR")));
    }

    @Test
    @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
    public void test2Success() throws Exception {

        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/35455/prices?date=2020-06-14T16:00:00")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(2)))
                .andExpect(jsonPath("$.startDate", is("2020-06-14T15:00:00")))
                .andExpect(jsonPath("$.endDate", is("2020-06-14T18:30:00")))
                .andExpect(jsonPath("$.finalPrice", is("25.45 EUR")));
    }

    @Test
    @DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
    public void test3Success() throws Exception {

        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/35455/prices?date=2020-06-14T21:00:00")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.startDate", is("2020-06-14T00:00:00")))
                .andExpect(jsonPath("$.endDate", is("2020-12-31T23:59:59")))
                .andExpect(jsonPath("$.finalPrice", is("35.5 EUR")));
    }

    @Test
    @DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)")
    public void test4Success() throws Exception {

        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/35455/prices?date=2020-06-15T10:00:00")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(3)))
                .andExpect(jsonPath("$.startDate", is("2020-06-15T00:00:00")))
                .andExpect(jsonPath("$.endDate", is("2020-06-15T11:00:00")))
                .andExpect(jsonPath("$.finalPrice", is("30.5 EUR")));
    }

    @Test
    @DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)")
    public void test5Success() throws Exception {

        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/35455/prices?date=2020-06-16T21:00:00")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(4)))
                .andExpect(jsonPath("$.startDate", is("2020-06-15T16:00:00")))
                .andExpect(jsonPath("$.endDate", is("2020-12-31T23:59:59")))
                .andExpect(jsonPath("$.finalPrice", is("38.95 EUR")));
    }

    @Test
    @DisplayName("Price not found")
    public void notFound() throws Exception {

        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/99/products/35455/prices?date=2020-06-16T21:00:00")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("Price not found.")));
    }

    @Test
    @DisplayName("Invalid Brand Id")
    public void wrongBrandId() throws Exception {

        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/invalid/products/35455/prices?date=2020-06-16T21:00:00")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.message", is("Invalid parameter: brandId.")));
    }

    @Test
    @DisplayName("Invalid Product Id")
    public void wrongProductId() throws Exception {

        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/invalid/prices?date=2020-06-16T21:00:00")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.message", is("Invalid parameter: productId.")));
    }

    @Test
    @DisplayName("Invalid Date")
    public void wrongDate() throws Exception {

        // Given
        // When
        // Then
        this.mockMvc
                .perform(
                        get("/brands/1/products/35455/prices?date=invalid")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.message", is("Invalid parameter: date.")));
    }
}
