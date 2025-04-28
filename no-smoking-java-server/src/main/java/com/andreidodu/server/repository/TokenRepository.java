package com.andreidodu.server.repository;


import com.andreidodu.server.entity.Token;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface TokenRepository extends TransactionalRepository<Token, Long> {
    List<Token> findByUser_id(Integer userId);

    List<Token> findByToken(String token);

    Collection<Object> findByTokenAndExpirationDatetimeAfter(String token, LocalDateTime expirationDatetimeAfter);
}
