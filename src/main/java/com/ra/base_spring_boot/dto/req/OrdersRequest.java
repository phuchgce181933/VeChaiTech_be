package com.ra.base_spring_boot.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrdersRequest {
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotNull(message = "Waste Listing ID is required")
    private Long wasteListingId;
    
    private String addressPublic;
    
    private String latitude;
    
    private String longitude;
    
    private String addressFull;
}
