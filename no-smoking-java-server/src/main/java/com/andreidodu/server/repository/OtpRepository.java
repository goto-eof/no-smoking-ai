package com.andreidodu.server.repository;


import com.andreidodu.server.entity.Otp;

import java.util.Optional;

public interface OtpRepository extends TransactionalRepository<Otp, Long> {
    Optional<Otp> findByOtpAndUuid(String otp, String uuid);
}
