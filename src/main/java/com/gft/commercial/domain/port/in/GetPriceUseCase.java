package com.gft.commercial.domain.port.in;

import com.gft.commercial.domain.model.Price;
import java.time.LocalDateTime;

public interface GetPriceUseCase {

    Price getPrice(Integer brand_id, Long productId, LocalDateTime date);

}
