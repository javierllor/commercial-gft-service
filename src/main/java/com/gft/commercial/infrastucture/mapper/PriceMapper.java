package com.gft.commercial.infrastucture.mapper;

import com.gft.commercial.domain.model.Price;
import com.gft.commercial.infrastucture.repository.entity.PriceEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PriceMapper {

    @Mapping(target = "startDate", source = "startDate", dateFormat = "yyyy-MM-dd-HH.mm.ss")
    @Mapping(target = "endDate", source = "endDate", dateFormat = "yyyy-MM-dd-HH.mm.ss")
    @Mapping(target = "finalPrice", expression = "java(price.getPrice() + \" \" + price.getCurrency())")
    Price mapPriceToPrice(PriceEntity price);

}
