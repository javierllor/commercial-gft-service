package com.gft.commercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.gft.commercial.application.service.PriceService;
import com.gft.commercial.application.usecase.GetPriceUseCaseImpl;
import com.gft.commercial.domain.model.Price;
import com.gft.commercial.infrastucture.adapter.PriceRepositoryAdapter;
import com.gft.commercial.infrastucture.repository.entity.PriceEntity;
import com.gft.commercial.infrastucture.exception.ResourceNotFoundException;
import com.gft.commercial.infrastucture.mapper.PriceMapperImpl;
import com.gft.commercial.infrastucture.repository.PriceRepository;
import jakarta.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {


    public static final Integer BRAND_ID = 1;
    public static final Long PRODUCT_ID = 35455L;
    public static final Long ID = 1L;
    public static final Integer PRIORITY = 0;
    public static final Integer PRICE_LIST = 2;
    public static final LocalDateTime DATE = LocalDateTime.of(2020, Month.JUNE, 14, 0, 0, 0);
    public static final String CURRENCY = "EUR";
    public static final Float PRICE = 35.5F;
    public static final String FORMATTED_DATE = DATE.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));

    @Mock
    private PriceRepository priceRepository;

    private PriceService priceService;

    @BeforeEach
    void setup() {
        priceService = new PriceService(
                new GetPriceUseCaseImpl(
                        new PriceRepositoryAdapter(priceRepository, new PriceMapperImpl())));
    }

    @Test
    void getPrice_Successful() {
        //Given
        when(priceRepository.findPriceEntity(
                eq(BRAND_ID), eq(PRODUCT_ID), eq(FORMATTED_DATE), eq(FORMATTED_DATE)))
                .thenReturn(Optional.of(PriceEntity.builder()
                        .id(ID)
                        .priority(PRIORITY)
                        .priceList(PRICE_LIST)
                        .brandId(BRAND_ID)
                        .productId(PRODUCT_ID)
                        .endDate(FORMATTED_DATE)
                        .startDate(FORMATTED_DATE)
                        .currency(CURRENCY)
                        .price(PRICE)
                        .build()));

        //When
        Price price = priceService.getPrice(BRAND_ID, PRODUCT_ID, DATE);

        //Then
        assertThat(price.getBrandId()).isEqualTo(BRAND_ID);
        assertThat(price.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(price.getPriceList()).isEqualTo(PRICE_LIST);
        assertThat(price.getFinalPrice()).isEqualTo(String.format("%s %s", PRICE, CURRENCY));
        assertThat(price.getStartDate()).isEqualTo(DATE);
        assertThat(price.getEndDate()).isEqualTo(DATE);
    }


    @Test
    void getPrice_NotFound() {
        //Given
        when(priceRepository.findPriceEntity(
                eq(BRAND_ID), eq(PRODUCT_ID), eq(FORMATTED_DATE), eq(FORMATTED_DATE)))
                .thenReturn(Optional.empty());

        //When
        //Then
        Assertions.assertThrows(ResourceNotFoundException.class, ()
                -> priceService.getPrice(BRAND_ID, PRODUCT_ID, DATE));
    }

    @Test
    void getPrice_FatalError() {
        //Given
        when(priceRepository.findPriceEntity(
                eq(BRAND_ID), eq(PRODUCT_ID), eq(FORMATTED_DATE), eq(FORMATTED_DATE)))
                .thenThrow(PersistenceException.class);

        //When
        //Then
        Assertions.assertThrows(Exception.class, ()
                -> priceService.getPrice(BRAND_ID, PRODUCT_ID, DATE));
    }

}
