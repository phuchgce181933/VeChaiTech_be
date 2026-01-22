package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import com.ra.base_spring_boot.model.constants.DepositStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet_deposit_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletDepositTransaction extends BaseObject {

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private WalletRecycler wallet;

    // ✅ THÊM TRỰC TIẾP
    @Column(name = "recycler_id", nullable = false)
    private Long recyclerId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(unique = true)
    private Long orderCode; // mã giao dịch PayOS

    @Enumerated(EnumType.STRING)
    private DepositStatus status;

    private String description;
}

