package com.andreidodu.server.scheduler;

import com.andreidodu.server.repository.UserRepository;
import com.andreidodu.server.service.SmokingDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTraining {
    private final SmokingDataService smokingDataService;
    private final UserRepository userRepository;

    /**
     * TODO it is better to create a spring job
     * TODO need to increase also performance here: execute the job only for yesterday and not for 31 days
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void runTrainerNightly() {
        userRepository.findAll()
                .forEach(user -> {
                    log.debug("Running trainer for user {}", user.getUsername());
                    IntStream.range(0, 31)
                            .forEach(day -> smokingDataService.populateDailySmokingData(user, LocalDateTime.now().minusDays(day)));

                    smokingDataService.trainModelByUserId(user.getId());
                });

    }

}
