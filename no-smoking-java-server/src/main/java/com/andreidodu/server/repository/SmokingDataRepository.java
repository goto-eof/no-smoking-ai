package com.andreidodu.server.repository;


import com.andreidodu.server.entity.SmokingData;
import com.andreidodu.server.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmokingDataRepository extends TransactionalRepository<SmokingData, Long> {
    Optional<SmokingData> findTopByUser_IdOrderByIdDesc(int userId);

    int countByUserAndDateTimeIsBetween(User user, LocalDateTime start, LocalDateTime end);

    Integer countByUser_IdAndDateTimeBetween(Integer id, LocalDateTime start, LocalDateTime end);
}
