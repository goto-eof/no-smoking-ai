package com.andreidodu.server.service;

public interface EmailService {

    void sendEmail(String to, String otp, String uuid);

}
