package com.andreidodu.server.controller;

import com.andreidodu.common.dto.api.OtpRequestDTO;
import com.andreidodu.common.dto.api.OtpResponseDTO;
import com.andreidodu.common.dto.api.OtpValidationRequestDTO;
import com.andreidodu.common.dto.api.OtpValidationResponseDTO;
import com.andreidodu.server.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/submit")
    public ResponseEntity<OtpResponseDTO> sendOtp(@RequestBody OtpRequestDTO otpRequestDTO) {
        return ResponseEntity.ok(otpService.generateOtp(otpRequestDTO.getEmail()));
    }

    @PostMapping("/verify")
    public ResponseEntity<OtpValidationResponseDTO> validateOtp(@RequestBody OtpValidationRequestDTO otpValidationRequestDTO) {
        return ResponseEntity.ok(otpService.validateOtp(otpValidationRequestDTO));
    }
}
