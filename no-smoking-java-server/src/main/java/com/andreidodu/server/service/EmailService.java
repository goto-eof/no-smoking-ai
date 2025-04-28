package com.andreidodu.server.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendEmail(String to, String otp, String uuid);
}
