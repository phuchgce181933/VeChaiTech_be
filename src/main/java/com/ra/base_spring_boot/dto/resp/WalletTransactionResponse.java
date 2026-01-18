package com.ra.base_spring_boot.dto.resp;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WalletTransactionResponse {
    
    private Long id;
    
    private Long walletId;
    
    private String type;
    
    private BigDecimal amount;
    
    private String description;
    
    private LocalDateTime transactionDate;
    
    private Long relatedOrderId;
    
    private String status;
    
    private BigDecimal balanceAfter;
    
    private String paymentMethod;
    
    private String referenceCode;
}
