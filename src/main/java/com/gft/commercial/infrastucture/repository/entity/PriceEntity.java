package com.gft.commercial.infrastucture.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prices")
public class PriceEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -6690183733368864288L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    private String startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    private String endDate;

    @Column(name = "price_list")
    private Integer priceList;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "price")
    private Float price;

    @Column(name = "currency")
    private String currency;
}
