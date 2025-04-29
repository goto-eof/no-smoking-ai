package com.andreidodu.common.dto;

import com.andreidodu.common.dto.common.DTOCommon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailySmokingDataDTO extends DTOCommon {

    private LocalDate date;
    private Integer weekday;
    private Boolean isHoliday;
    private Integer cigarettesSmokedCount;

}
