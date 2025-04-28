package com.andreidodu.server.service.impl;

import com.andreidodu.server.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Async
    @Override
    public void sendEmail(String to, String otp, String uuid) {
        log.debug("Sending email to {}", to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject("Your OTP Code");
        message.setText("OTP: " + otp + "\nUUID: " + uuid);
        mailSender.send(message);
        log.debug("Email sent!");
    }

}
