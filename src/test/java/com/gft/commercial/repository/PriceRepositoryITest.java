package com.gft.commercial.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.gft.commercial.infrastucture.repository.entity.PriceEntity;
import com.gft.commercial.infrastucture.repository.PriceRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@Sql(
        scripts = {"/data/prices_schema.sql", "/data/insert_prices.sql"},
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/data/delete_prices.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PriceRepositoryITest {

    private static final Integer BRAND_ID = 1;
    private static final Long PRODUCT_ID = 35455L;

    private static final String DATE = "2020-06-14-10.00.00";

    private static final String START_DATE = "2020-06-14-00.00.00";
    private static final String END_DATE = "2020-12-31-23.59.59";

    @Autowired
    private PriceRepository priceRepository;


    @Test
    void getPrice_Successful() {
        //Given
        //When
        Optional<PriceEntity> priceEntity =
                priceRepository.findPriceEntity(BRAND_ID, PRODUCT_ID, DATE, DATE);

        //Then
        priceEntity.ifPresent(entity -> {
            assertThat(entity.getId()).isEqualTo(1);
            assertThat(entity.getBrandId()).isEqualTo(BRAND_ID);
            assertThat(entity.getProductId()).isEqualTo(PRODUCT_ID);
            assertThat(entity.getStartDate()).isEqualTo(START_DATE);
            assertThat(entity.getEndDate()).isEqualTo(END_DATE);
            assertThat(entity.getPriceList()).isEqualTo(1);
            assertThat(entity.getPrice()).isEqualTo(35.5F);
            assertThat(entity.getPriority()).isEqualTo(0);
            assertThat(entity.getCurrency()).isEqualTo("EUR");
        });

    }

    @Test
    void getPrice_NotFound() {
        //Given
        //When
        Optional<PriceEntity> priceEntity = priceRepository.findPriceEntity(BRAND_ID, 7777L, DATE, DATE);
        //Then
        assertThat(priceEntity).isEmpty();
    }


}
