package com.gft.commercial.application.usecase;

import com.gft.commercial.domain.model.Price;
import com.gft.commercial.domain.port.in.GetPriceUseCase;
import com.gft.commercial.domain.port.out.PriceRepositoryPort;
import com.gft.commercial.infrastucture.exception.ResourceNotFoundException;
import java.time.LocalDateTime;

public class GetPriceUseCaseImpl implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    public GetPriceUseCaseImpl(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    @Override
    public Price getPrice(Integer brand_id, Long productId, LocalDateTime date) {
        return priceRepositoryPort.getPriceByBrandAndProductAndByDate(brand_id, productId, date)
                .orElseThrow(() -> new ResourceNotFoundException("Price"));
    }
}
