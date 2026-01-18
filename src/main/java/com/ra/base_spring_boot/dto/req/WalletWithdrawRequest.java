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
public class WalletWithdrawRequest {
    
    @NotNull(message = "Recycler ID is required")
    private Long recyclerId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "10000", message = "Minimum withdraw amount is 10,000 VND")
    private BigDecimal amount;
    
    @NotNull(message = "Bank account is required")
    private String bankAccount;
    
    private String description;
}
