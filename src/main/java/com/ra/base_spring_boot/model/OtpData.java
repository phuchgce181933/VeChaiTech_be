package com.ra.base_spring_boot.model;

public class OtpData {
    private String otp;
    private long expiredAt;

    public OtpData(String otp, long expiredAt) {
        this.otp = otp;
        this.expiredAt = expiredAt;
    }

    public String getOtp() {
        return otp;
    }

    public long getExpiredAt() {
        return expiredAt;
    }
}
