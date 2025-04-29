package com.andreidodu.server.repository;


import com.andreidodu.server.entity.Otp;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepository extends TransactionalRepository<Otp, Long> {
    Optional<Otp> findByOtpAndUuid(String otp, String uuid);

    Optional<Otp> findByUser_EmailAndUuidAndExpirationDatetimeAfter(String email, String uuid, LocalDateTime now);
}
