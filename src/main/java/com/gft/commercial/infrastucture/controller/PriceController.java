package com.gft.commercial.infrastucture.controller;

import com.gft.commercial.application.service.PriceService;
import com.gft.commercial.domain.model.Price;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/brands/{brandId}/products/{productId}/prices")
    public ResponseEntity<Price> getPrice(
            @PathVariable Integer brandId,
            @PathVariable Long productId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        logger.debug("In getPrice controller with brandId: {}, productId: {} and date: {}",
                brandId, productId, date);
        return ResponseEntity.ok(priceService.getPrice(brandId, productId, date));
    }
}
