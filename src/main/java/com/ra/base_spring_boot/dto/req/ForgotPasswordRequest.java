package com.ra.base_spring_boot.dto.req;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String identifier; // email hoặc phone
    private String deliveryMethod; // EMAIL | SMS
}
