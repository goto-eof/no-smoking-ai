package com.andreidodu.server.scheduler;

import com.andreidodu.server.repository.UserRepository;
import com.andreidodu.server.service.SmokingDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTraining {
    private final SmokingDataService smokingDataService;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void runTrainerNightly() {
        userRepository.findAll().forEach(user -> {
            smokingDataService.populateDailySmokingData(LocalDateTime.now());
            smokingDataService.trainModelByUserId(user.getId());
        });

    }

}
