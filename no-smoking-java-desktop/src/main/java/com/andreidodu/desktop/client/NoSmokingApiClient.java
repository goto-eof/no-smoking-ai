package com.andreidodu.desktop.client;

import com.andreidodu.common.dto.SmokingDataDTO;
import com.andreidodu.common.dto.api.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "noSmokingApiClient", url = "${application.server.api.url}")
public interface NoSmokingApiClient {

    @PostMapping("/api/v1/otp/submit")
    OtpResponseDTO sendEmail(@RequestBody OtpRequestDTO otpRequestDTO);

    @PostMapping("/api/v1/otp/verify")
    OtpValidationResponseDTO verifyOtp(@RequestBody OtpValidationRequestDTO otpRequestDTO);

    @PostMapping("/api/v1/cigarette/plus")
    SmokingDataDTO plus(@RequestParam("token") String token);

    @DeleteMapping("/api/v1/cigarette/minus")
    Optional<SmokingDataDTO> minus(@RequestParam("token") String token);

    @GetMapping("/api/v1/cigarette/lastInserted")
    Optional<SmokingDataDTO> lastInserted(@RequestParam("token") String token);

    @GetMapping("/api/v1/cigarette/counters")
    CountersDTO getCounters(@RequestParam("token") String token);
}