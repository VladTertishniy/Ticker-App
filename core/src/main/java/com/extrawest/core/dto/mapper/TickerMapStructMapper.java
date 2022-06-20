package com.extrawest.core.dto.mapper;

import com.extrawest.core.dto.feign.TickerFeignDTO;
import com.extrawest.core.dto.response.TickerResponseDTO;
import com.extrawest.core.model.Ticker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TickerMapStructMapper {
    @Mapping(source = "id", target = "tickerId")
    @Mapping(source = "owner.email", target = "userEmail")
    @Mapping(source = "tickInterval", target = "interval")
    TickerFeignDTO toTickerFeignDTO(Ticker ticker);

    TickerResponseDTO toTickerResponseDTO (Ticker ticker);
}
