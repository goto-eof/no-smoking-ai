package com.andreidodu.server.service.impl;

import com.andreidodu.common.dto.api.OtpResponseDTO;
import com.andreidodu.common.dto.api.OtpValidationRequestDTO;
import com.andreidodu.common.dto.api.OtpValidationResponseDTO;
import com.andreidodu.server.entity.Otp;
import com.andreidodu.server.entity.Token;
import com.andreidodu.server.entity.User;
import com.andreidodu.server.repository.OtpRepository;
import com.andreidodu.server.repository.TokenRepository;
import com.andreidodu.server.repository.UserRepository;
import com.andreidodu.server.service.EmailService;
import com.andreidodu.server.service.JwtService;
import com.andreidodu.server.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    @Value("${application.security.jwt.expiration-millis}")
    private long expirationMillis;

    @Override
    public OtpResponseDTO generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        String uuid = UUID.randomUUID().toString();

        emailService.sendEmail(email, otp, uuid);

        User user = buildUser(email);

        Otp otpModel = new Otp();
        otpModel.setUuid(uuid);
        otpModel.setOtp(otp);
        otpModel.setUser(user);
        otpModel.setExpirationDatetime(LocalDateTime.now().plusMinutes(15));
        otpModel = otpRepository.save(otpModel);

        return OtpResponseDTO.builder()
                .result(true)
                .build();
    }

    private User buildUser(String email) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User model = new User();
                    model.setEmail(email);
                    model.setUsername(email);
                    model.setRole("USER");
                    model.setPassword(UUID.randomUUID().toString());
                    return userRepository.save(model);
                });
    }

    @Override
    public OtpValidationResponseDTO validateOtp(OtpValidationRequestDTO otpValidationRequestDTO) {
        Optional<Otp> otpOptional = otpRepository.findByUser_EmailAndUuidAndExpirationDatetimeAfter(otpValidationRequestDTO.getEmail(), otpValidationRequestDTO.getUuid(), LocalDateTime.now());

        if (otpOptional.isEmpty()) {
            return OtpValidationResponseDTO
                    .builder()
                    .build();
        }

        Otp otp = otpOptional.get();
        User user = otp.getUser();
        Date expirationDate = new Date(System.currentTimeMillis() + expirationMillis);
        String jwt = jwtService.generateToken(user.getId(), user.getEmail(), expirationDate);

        Token tokenModel = new Token();
        tokenModel.setToken(jwt);
        tokenModel.setUser(user);
        tokenModel.setExpirationDatetime(expirationDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        tokenRepository.save(tokenModel);

        return OtpValidationResponseDTO
                .builder()
                .token(jwt)
                .build();


    }

}
