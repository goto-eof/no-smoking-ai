package com.andreidodu.server.service;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.Optional;

public interface JwtService {

    String generateToken(Integer userId, String email, Date expirationDate);

    boolean isValidToken(String token);

    Optional<Claims> parseToken(String token);

}
