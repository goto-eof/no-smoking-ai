package com.andreidodu.server.service.impl;

import com.andreidodu.server.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {
    private final SecretKey key;

    public JwtServiceImpl(@Value("${application.security.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    @Override
    public String generateToken(Integer userId, String email, Date expirationDate) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public UserData extractUserDataFromAuthentication(Authentication authentication) {
        String token = extractTokenFromAuthentication(authentication);
        Claims claims = extractAllClaims(token);

        Integer userId = claims.get("userId", Integer.class);
        String email = claims.get("email", String.class);

        return new UserData(userId, email);
    }


    @Override
    public Optional<Claims> parseToken(String token) {
        try {
            JwtParser parser = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build();
            Jws<Claims> jwsClaims = parser.parseClaimsJws(token);
            return Optional.of(jwsClaims.getBody());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private String extractTokenFromAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authentication is missing or invalid");
        }
        return authentication.getCredentials().toString();
    }

    public static class UserData {
        private final Integer userId;
        private final String email;

        public UserData(Integer userId, String email) {
            this.userId = userId;
            this.email = email;
        }

        public Integer getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }
    }
}

