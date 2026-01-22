package com.ra.base_spring_boot.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.base_spring_boot.configuration.PayOSConfig;
import com.ra.base_spring_boot.dto.req.PayOSCreatePaymentRequest;
import com.ra.base_spring_boot.dto.resp.PayOSCreatePaymentResponse;
import com.ra.base_spring_boot.services.IPayOSService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class PayOSServiceImpl implements IPayOSService {
    private final PayOS payOS;

    @Value("${payos.return-url}")
    private String returnUrl;

    @Value("${payos.cancel-url}")
    private String cancelUrl;

    @Override
    public String createPaymentLink(Long orderCode, BigDecimal amount, String description) {
        try {
            CreatePaymentLinkRequest request =
                    CreatePaymentLinkRequest.builder()
                            .orderCode(orderCode)
                            .amount(amount.longValue())
                            .description(description)
                            .returnUrl(returnUrl)
                            .cancelUrl(cancelUrl)
                            .build();

            var response = payOS.paymentRequests().create(request);
            return response.getCheckoutUrl();

        } catch (Exception e) {
            throw new RuntimeException("Không tạo được link PayOS", e);
        }
    }

    @Override
    public boolean verifyWebhookSignature(String rawBody, String signature) {
        return false;
    }
}
