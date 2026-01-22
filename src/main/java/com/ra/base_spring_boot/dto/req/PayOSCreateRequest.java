package com.ra.base_spring_boot.dto.req;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayOSCreateRequest {
    private Long orderCode;
    private BigDecimal amount;
    private String description;
    private String returnUrl;
    private String cancelUrl;
}
