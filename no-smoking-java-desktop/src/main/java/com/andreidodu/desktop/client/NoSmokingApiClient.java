package com.andreidodu.desktop.client;

import com.andreidodu.common.dto.SmokingDataDTO;
import com.andreidodu.common.dto.api.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "noSmokingApiClient", url = "http://localhost:8081")
public interface NoSmokingApiClient {

    @PostMapping("/api/v1/otp/submit")
    OtpResponseDTO sendOtp(@RequestBody OtpRequestDTO otpRequestDTO);

    @PostMapping("/api/v1/otp/verify")
    OtpValidationResponseDTO sendOtp(@RequestBody OtpValidationRequestDTO otpRequestDTO);

    @PostMapping("/api/v1/cigarette/plus")
    SmokingDataDTO plus();

    @DeleteMapping("/api/v1/cigarette/minus")
    Optional<SmokingDataDTO> minus();

    @GetMapping("/api/v1/cigarette/lastInserted")
    Optional<SmokingDataDTO> lastInserted();

    @GetMapping("/api/v1/cigarette/counters")
    CountersDTO getCounters();
}