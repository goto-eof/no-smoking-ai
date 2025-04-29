package com.andreidodu.server.service;

import com.andreidodu.common.dto.DailySmokingDataDTO;
import com.andreidodu.common.dto.SmokingDataDTO;
import com.andreidodu.common.dto.api.CountersDTO;
import com.andreidodu.server.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface SmokingDataService {
    SmokingDataDTO add();

    Optional<SmokingDataDTO> deleteLastInserted();

    Optional<SmokingDataDTO> getLastItemInserted();

    int countByDate(LocalDate date);

    DailySmokingDataDTO populateDailySmokingData(User user, LocalDateTime dateTime);

    Integer addAndPopulateAndTrainAndRetrieveDailySmokingData(LocalDateTime dateTime);

    Integer getPredictionByDate(int userId, LocalDate now);

    String trainModelByUserId(Integer userId);

    Integer getPrediction(LocalDate date);

    CountersDTO getCounters();
}
