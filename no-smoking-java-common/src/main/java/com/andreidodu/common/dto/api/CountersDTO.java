package com.andreidodu.common.dto.api;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountersDTO {

    private Integer currentCount;
    private Integer predictionCount;

}
