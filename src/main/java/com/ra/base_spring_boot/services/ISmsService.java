package com.ra.base_spring_boot.services;

public interface ISmsService {
    void sendOtp(String phoneNumber, String otp);
}
