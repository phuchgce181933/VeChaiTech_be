package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.model.Orders;
import com.ra.base_spring_boot.model.WalletTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface IWalletService {
    /**
     * Xử lý webhook PayOS khi thanh toán nạp tiền thành công
     * - Idempotent (không cộng tiền trùng)
     * - Update WalletDepositTransaction
     * - Update WalletRecycler
     * - Tạo WalletTransaction
     */
    void depositSuccess(Long orderCode);

    /**
     * Lấy số dư ví hiện tại của recycler
     */
    BigDecimal getBalance(Long recyclerId);

    /**
     * Lấy lịch sử giao dịch ví
     */
    List<?> getTransactions(Long recyclerId);
}
