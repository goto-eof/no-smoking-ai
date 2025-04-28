package com.andreidodu.common.dto.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CountersDTO {

    private Integer currentCount;
    private Integer predictionCount;

}
