package com.ra.base_spring_boot.dto.req;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WalletDepositRequest {
    
    @NotNull(message = "Recycler ID is required")
    private Long recyclerId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "10000", message = "Minimum deposit amount is 10,000 VND")
    private BigDecimal amount;
    
    @NotNull(message = "Payment method is required")
    private String paymentMethod; // BANK_TRANSFER, CREDIT_CARD, E_WALLET
    
    private String description;
    
    private String referenceCode; // Mã từ payment gateway
}
