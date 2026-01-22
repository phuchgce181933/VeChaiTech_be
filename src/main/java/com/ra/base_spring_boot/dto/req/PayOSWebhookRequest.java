package com.ra.base_spring_boot.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayOSWebhookRequest {
    private String code;
    private String desc;
    private PayOSWebhookData data;

    @Getter
    @Setter
    public static class PayOSWebhookData {
        private Long orderCode;
        private String status;
        private Integer amount;
    }

    public boolean isSuccess() {
        return data != null && "PAID".equalsIgnoreCase(data.getStatus());
    }

    public Long getOrderCode() {
        return data.getOrderCode();
    }
}
