package com.andreidodu.server.controller;

import com.andreidodu.common.dto.SmokingDataDTO;
import com.andreidodu.common.dto.api.CountersDTO;
import com.andreidodu.server.service.SmokingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/minus")
    public ResponseEntity<Optional<SmokingDataDTO>> minus() {
        return ResponseEntity.ok(smokingDataService.deleteLastInserted());
    }

    @GetMapping("/lastInserted")
    public ResponseEntity<Optional<SmokingDataDTO>> lastInserted() {
        return ResponseEntity.ok(smokingDataService.getLastItemInserted());
    }

    @GetMapping("/counters")
    public ResponseEntity<CountersDTO> getCounters() {
        return ResponseEntity.ok(smokingDataService.getCounters());
    }
}
