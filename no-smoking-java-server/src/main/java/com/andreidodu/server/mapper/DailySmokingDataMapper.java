package com.andreidodu.server.mapper;

import com.andreidodu.common.dto.DailySmokingDataDTO;
import com.andreidodu.server.entity.DailySmokingData;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {})
public abstract class DailySmokingDataMapper {

    @Mapping(target = "user", ignore = true)
    public abstract DailySmokingData toModel(DailySmokingDataDTO dailySmokingDataDTO);

    public abstract DailySmokingDataDTO toDTO(DailySmokingData dailySmokingDataDTO);

}
