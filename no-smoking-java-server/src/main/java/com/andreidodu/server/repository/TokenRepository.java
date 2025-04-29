package com.andreidodu.server.repository;


import com.andreidodu.server.entity.Token;

import java.time.LocalDateTime;
import java.util.Collection;

public interface TokenRepository extends TransactionalRepository<Token, Long> {

    Collection<Object> findByTokenAndExpirationDatetimeAfter(String token, LocalDateTime expirationDatetimeAfter);

}
