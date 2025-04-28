package com.andreidodu.common.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DTOCommon {

    protected Integer id;
    protected LocalDateTime createdDate;
    protected LocalDateTime lastModifiedDate;
}
