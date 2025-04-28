package com.andreidodu.server.service;

import com.andreidodu.server.service.impl.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.Optional;

public interface JwtService {
    String generateToken(Integer userId, String email, Date expirationDate);

    boolean isValidToken(String token);

    JwtServiceImpl.UserData extractUserDataFromAuthentication(Authentication authentication);

    Optional<Claims> parseToken(String token);
}
