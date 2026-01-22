package com.ra.base_spring_boot.dto.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayOSCreatePaymentResponse {
    private String code;
    private String desc;
    private PayOSData data;

    @Getter
    @Setter
    public static class PayOSData {
        private String checkoutUrl;
    }
}
