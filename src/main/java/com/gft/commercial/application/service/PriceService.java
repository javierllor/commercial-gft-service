package com.gft.commercial.application.service;

import com.gft.commercial.domain.model.Price;
import com.gft.commercial.domain.port.in.GetPriceUseCase;
import java.time.LocalDateTime;

public class PriceService implements GetPriceUseCase {

    private final GetPriceUseCase getPriceUseCase;

    public PriceService(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @Override
    public Price getPrice(Integer brand_id, Long productId, LocalDateTime date) {
        return getPriceUseCase.getPrice(brand_id, productId, date);
    }
}
