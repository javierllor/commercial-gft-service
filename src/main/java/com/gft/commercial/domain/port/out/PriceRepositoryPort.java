package com.gft.commercial.domain.port.out;

import com.gft.commercial.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {

    Optional<Price> getPriceByBrandAndProductAndByDate(Integer brand_id, Long productId, LocalDateTime date);
}
