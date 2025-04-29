package com.andreidodu.common.dto;

import com.andreidodu.common.dto.common.DTOCommon;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailySmokingDataDTO extends DTOCommon {

    private LocalDate date;
    private Integer weekday;
    private Boolean isHoliday;
    private Integer cigarettesSmokedCount;

}
