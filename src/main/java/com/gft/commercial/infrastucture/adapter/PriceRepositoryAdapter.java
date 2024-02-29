package com.gft.commercial.infrastucture.adapter;

import com.gft.commercial.domain.model.Price;
import com.gft.commercial.domain.port.out.PriceRepositoryPort;
import com.gft.commercial.infrastucture.mapper.PriceMapper;
import com.gft.commercial.infrastucture.repository.PriceRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public PriceRepositoryAdapter(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public Optional<Price> getPriceByBrandAndProductAndByDate(Integer brand_id, Long productId, LocalDateTime date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));

        return priceRepository.findPriceEntity(brand_id, productId, formattedDate, formattedDate)
                .map(priceMapper::mapPriceToPrice);
    }
}
