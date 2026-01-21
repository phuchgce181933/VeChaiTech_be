package com.ra.base_spring_boot.dto.req;

import lombok.Data;

@Data
public class  CancelOrderRequest {
   private Long userId;
    private String reason;
    private String evidenceUrl;
}
