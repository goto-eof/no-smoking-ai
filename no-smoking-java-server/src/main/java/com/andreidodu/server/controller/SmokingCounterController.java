package com.andreidodu.server.controller;

import com.andreidodu.common.dto.SmokingDataDTO;
import com.andreidodu.server.service.SmokingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cigarette")
@RequiredArgsConstructor
public class SmokingCounterController {
    private final SmokingDataService smokingDataService;

    @PostMapping("/plus")
    public ResponseEntity<SmokingDataDTO> plus() {
        return ResponseEntity.ok(smokingDataService.add());
    }

    @PostMapping("/minus")
    public ResponseEntity<Optional<SmokingDataDTO>> minus() {
        return ResponseEntity.ok(smokingDataService.deleteLastInserted());
    }

}
