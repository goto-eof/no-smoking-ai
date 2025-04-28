package com.andreidodu.common.dto;

import com.andreidodu.common.dto.common.DTOCommon;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SmokingDataDTO extends DTOCommon {

    private LocalDateTime dateTime;

}
