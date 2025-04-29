package com.andreidodu.server.service;

import com.andreidodu.common.dto.api.OtpResponseDTO;
import com.andreidodu.common.dto.api.OtpValidationRequestDTO;
import com.andreidodu.common.dto.api.OtpValidationResponseDTO;

public interface OtpService {

    OtpResponseDTO generateOtp(String email);

    OtpValidationResponseDTO validateOtp(OtpValidationRequestDTO otpValidationRequestDTO);

}
