package com.ra.base_spring_boot.configuration;

public class OtpUtil {
    public static String generateOtp() {
        int otp = (int) (Math.random() * 900000) + 100000; // random 6 số
        return String.valueOf(otp);
    }
    public static long generateExpiryTime() {
        return System.currentTimeMillis() + (5 * 60 * 1000); // 5 phút
    }

    public static boolean isExpired(long expiryTime) {
        return System.currentTimeMillis() > expiryTime;
    }
}
