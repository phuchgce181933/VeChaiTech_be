package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "wallet_transaction")
public class WalletTransaction extends BaseObject {

    @ManyToOne
    @JoinColumn(name = "wallet_recycler_id")
    private WalletRecycler wallet;

    @Column(name = "type")
    private String type; // DEPOSIT (nạp tiền), WITHDRAW (rút tiền), PAYMENT (thanh toán), REFUND (hoàn tiền)

    @Column(name = "amount", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "related_order_id")
    private Orders relatedOrder; // Đơn hàng liên quan (nếu là PAYMENT)

    @Column(name = "status")
    private String status; // PENDING, SUCCESS, FAILED, CANCELLED

    @Column(name = "balance_after", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal balanceAfter; // Số dư sau giao dịch

    @Column(name = "payment_method")
    private String paymentMethod; // BANK_TRANSFER, CREDIT_CARD, E_WALLET, etc.

    @Column(name = "reference_code")
    private String referenceCode; // Mã tham chiếu từ payment gateway
}
