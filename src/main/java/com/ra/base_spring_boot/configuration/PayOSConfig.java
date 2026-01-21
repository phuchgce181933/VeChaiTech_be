package com.ra.base_spring_boot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class PayOSConfig {
    @Value("${payos.client-id}")
    private String clientId;

    @Value("${payos.api-key}")
    private String apiKey;

    @Value("${payos.checksum-key}")
    private String checksumKey;

//    @Bean
//    public PayOS payOS() {
//        return new PayOS(clientId, apiKey, checksumKey);
//    }
}
