package com.gft.commercial.infrastucture.repository;

import com.gft.commercial.infrastucture.repository.entity.PriceEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p " +
            "WHERE p.brandId = :brandId " +
            "AND p.productId = :productId " +
            "AND PARSEDATETIME(p.startDate, 'yyyy-MM-dd-HH.mm.ss') <= PARSEDATETIME(:startDate, 'yyyy-MM-dd-HH.mm.ss') "
            +
            "AND PARSEDATETIME(p.endDate, 'yyyy-MM-dd-HH.mm.ss') >= PARSEDATETIME(:endDate, 'yyyy-MM-dd-HH.mm.ss') " +
            "ORDER BY p.priority DESC LIMIT 1")
    Optional<PriceEntity> findPriceEntity(
            Integer brandId, Long productId, String startDate, String endDate);
}
