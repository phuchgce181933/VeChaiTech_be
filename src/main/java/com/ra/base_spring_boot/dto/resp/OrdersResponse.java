package com.ra.base_spring_boot.dto.resp;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrdersResponse {
    
    private Long id;
    
    private String customerName;
    
    private Long customerId;
    
    private String recyclerName;
    
    private Long recyclerId;
    
    private String wasteListingName;
    
    private Long wasteListingId;
    
    private BigDecimal quantity;
    
    private BigDecimal totalPrice;
    
    private String addressPublic;
    
    private String latitude;
    
    private String longitude;
    
    private String addressFull;

    private String customerPhone;
    
    private String status;
}
