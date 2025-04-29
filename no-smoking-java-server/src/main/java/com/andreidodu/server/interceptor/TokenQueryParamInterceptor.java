package com.andreidodu.server.interceptor;

import com.andreidodu.server.entity.User;
import com.andreidodu.server.repository.TokenRepository;
import com.andreidodu.server.repository.UserRepository;
import com.andreidodu.server.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenQueryParamInterceptor extends OncePerRequestFilter {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("uri: {}", request.getRequestURI());
        String token = request.getParameter("token");
        log.debug("requestToken: {}", token);

        if (isValidToken(token) && isNotAuthenticated()) {
            Optional.ofNullable(buildAuthenticationFromToken(token))
                    .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        }

        filterChain.doFilter(request, response);
    }

    private static boolean isNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private Authentication buildAuthenticationFromToken(String token) {
        if (!isValidToken(token)) {
            return null;
        }

        Optional<Claims> authentication = jwtService.parseToken(token);
        if (authentication.isEmpty()) {
            return null;
        }
        Claims claims = authentication.get();
        User user = userRepository.findById(claims.get("userId", Integer.class)).orElseThrow();


        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole())
        );

        return new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);
    }

    private boolean isValidToken(String token) {

        if (token == null || token.isBlank()) {
            log.debug("token is blank");
            return false;
        }

        if (token.length() < 20) {
            log.debug("token is too short");
            return false;
        }

        if (!token.matches("^[A-Za-z0-9\\-\\._~\\+\\/]+=*$")) {
            log.debug("token has invalid pattern");
            return false;
        }

        if (!jwtService.isValidToken(token)) {
            log.debug("token content is invalid");
            return false;
        }

        boolean isOnDb = tokenRepository.findByTokenAndExpirationDatetimeAfter(token, LocalDateTime.now())
                .stream()
                .findFirst()
                .isPresent();

        if (!isOnDb) {
            log.debug("token expired");
            return false;
        }

        return true;
    }
}