package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.services.ISmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements ISmsService {
    private final RestTemplate restTemplate = new RestTemplate();

    // có thể chuyển sang application.properties để dễ quản lý
    private static final String API_KEY = "a91dbad8-8208-4661-aebc-70afa9ecf388";
    private static final String DEVICE_ID = "68747523c430dcc62c1ef2fa";
    private static final String BASE_URL = "https://api.textbee.dev/api/v1";

    @Override
    public void sendOtp(String phoneNumber, String otp) {
        try {
            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", API_KEY); // ✅ Đúng với TextBee

            // Body
            Map<String, Object> body = new HashMap<>();
            body.put("recipients", Collections.singletonList(phoneNumber));
            body.put("message", "Your OTP code is: " + otp);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            // ✅ URL đúng format
            String url = BASE_URL + "/gateway/devices/" + DEVICE_ID + "/send-sms";

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("OTP sent via SMS to " + phoneNumber);
            } else {
                System.err.println("Failed to send OTP SMS: " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
