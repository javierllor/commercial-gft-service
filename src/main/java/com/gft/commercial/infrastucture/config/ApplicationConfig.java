package com.gft.commercial.infrastucture.config;

import com.gft.commercial.application.service.PriceService;
import com.gft.commercial.application.usecase.GetPriceUseCaseImpl;
import com.gft.commercial.domain.port.out.PriceRepositoryPort;
import com.gft.commercial.infrastucture.adapter.PriceRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public PriceService priceService(PriceRepositoryPort priceRepositoryPort) {
        return new PriceService(
                new GetPriceUseCaseImpl(priceRepositoryPort)
        );
    }

    @Bean
    PriceRepositoryPort priceRepositoryPort(PriceRepositoryAdapter priceRepositoryAdapter) {
        return priceRepositoryAdapter;
    }
}
