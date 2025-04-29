package com.andreidodu.common.dto;

import com.andreidodu.common.dto.common.DTOCommon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmokingDataDTO extends DTOCommon {

    private LocalDateTime dateTime;

}
