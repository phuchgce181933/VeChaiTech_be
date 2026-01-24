package com.ra.base_spring_boot.dto.req;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String identifier; // email hoặc phone
    private String otp;
    private String newPassword;
}
