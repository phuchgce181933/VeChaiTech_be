package com.ra.base_spring_boot.services;

import java.math.BigDecimal;
import java.util.Map;

public interface IPayOSService {
    String createPaymentLink(Long orderCode, BigDecimal amount, String description);
    boolean verifyWebhookSignature(String rawBody, String signature);
}
