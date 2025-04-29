package com.andreidodu.common.dto.api;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpValidationRequestDTO {

    private String uuid;
    private String email;

}
