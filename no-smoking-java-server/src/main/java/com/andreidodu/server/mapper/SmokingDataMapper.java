package com.andreidodu.server.mapper;

import com.andreidodu.common.dto.SmokingDataDTO;
import com.andreidodu.server.entity.SmokingData;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {})
public abstract class SmokingDataMapper {
    @Mapping(target = "user", ignore = true)
    public abstract SmokingData toModel(SmokingDataDTO dto);

    public abstract SmokingDataDTO toDTO(SmokingData model);

}
