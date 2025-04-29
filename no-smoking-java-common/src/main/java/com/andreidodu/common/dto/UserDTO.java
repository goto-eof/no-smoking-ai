package com.andreidodu.common.dto;


import com.andreidodu.common.dto.common.DTOCommon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends DTOCommon {

    private String username;
    private String email;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<DailySmokingDataDTO> dailySmokingDataList;
    private List<SmokingDataDTO> smokingDataList;

}

