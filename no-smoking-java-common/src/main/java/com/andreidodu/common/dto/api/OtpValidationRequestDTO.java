package com.andreidodu.common.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpValidationRequestDTO {

    private String otp;
    private String uuid;

}
