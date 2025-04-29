package com.andreidodu.common.dto;


import com.andreidodu.common.dto.common.DTOCommon;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends DTOCommon {

    private String username;
    private String email;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<DailySmokingDataDTO> dailySmokingDataList;
    private List<SmokingDataDTO> smokingDataList;

}

