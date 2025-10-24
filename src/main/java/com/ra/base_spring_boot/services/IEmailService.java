package com.ra.base_spring_boot.services;

public interface IEmailService {
    void sendOtp(String to, String otp);
}
