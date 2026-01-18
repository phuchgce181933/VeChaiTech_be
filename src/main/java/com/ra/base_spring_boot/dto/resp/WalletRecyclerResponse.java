package com.ra.base_spring_boot.dto.resp;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WalletRecyclerResponse {
    
    private Long id;
    
    private Long recyclerId;
    
    private String recyclerName;
    
    private BigDecimal balance;
    
    private BigDecimal totalDeposited;
    
    private BigDecimal totalSpent;
    
    private Boolean isActive;
}
