package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "wallet_recycler")
public class WalletRecycler extends BaseObject {

    @OneToOne
    @JoinColumn(name = "recycler_id", unique = true)
    private User recycler;

    @Column(name = "balance", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal balance; // Số tiền hiện có trong ví

    @Column(name = "total_deposited", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal totalDeposited; // Tổng đã nạp

    @Column(name = "total_spent", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal totalSpent; // Tổng đã chi để nhận đơn

    @Column(name = "is_active")
    private Boolean isActive; // true = ví hoạt động, false = bị khóa

    // ✅ constructor cũ
    public WalletRecycler(User user) {
        this.recycler = user;
        this.balance = BigDecimal.ZERO;
        this.totalDeposited = BigDecimal.ZERO;
        this.totalSpent = BigDecimal.ZERO;
    }

    // ✅ constructor MỚI – QUAN TRỌNG
    public WalletRecycler(Long recyclerId) {
        this.recycler = new User();
        this.recycler.setId(recyclerId);

        this.balance = BigDecimal.ZERO;
        this.totalDeposited = BigDecimal.ZERO;
        this.totalSpent = BigDecimal.ZERO;
    }
}
