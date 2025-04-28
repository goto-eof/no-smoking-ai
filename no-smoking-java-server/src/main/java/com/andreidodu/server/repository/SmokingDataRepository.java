package com.andreidodu.server.repository;


import com.andreidodu.server.entity.SmokingData;
import com.andreidodu.server.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmokingDataRepository extends TransactionalRepository<SmokingData, Long> {
    Optional<SmokingData> findTopByUser_IdOrderByIdDesc(int userId);

    int countByCreatedDateIsBetween(LocalDateTime createdDateAfter, LocalDateTime createdDateBefore);

    Integer countByUser_IdAndCreatedDateBetween(int userId, LocalDateTime start, LocalDateTime end);

    int countByUserAndDateTimeIsBetween(User user, LocalDateTime start, LocalDateTime end);
}
