package com.andreidodu.server.repository;


import com.andreidodu.server.entity.DailySmokingData;

import java.time.LocalDate;
import java.util.Optional;

public interface DailySmokingDataRepository extends TransactionalRepository<DailySmokingData, Long> {

    Optional<DailySmokingData> findByUser_idAndDate(Integer id, LocalDate date);

    LocalDate date(LocalDate date);

}
