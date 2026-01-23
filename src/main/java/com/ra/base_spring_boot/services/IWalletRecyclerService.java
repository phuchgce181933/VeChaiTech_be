package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.WalletDepositRequest;
import com.ra.base_spring_boot.dto.req.WalletWithdrawRequest;
import com.ra.base_spring_boot.dto.resp.WalletRecyclerResponse;
import com.ra.base_spring_boot.dto.resp.WalletTransactionResponse;
import com.ra.base_spring_boot.model.WalletRecycler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IWalletRecyclerService {
    // Thanh toán để nhận đơn
    WalletTransactionResponse payForOrder(Long recyclerId, Long orderId, BigDecimal amount);
    // Kiểm tra số dư đủ để nhận đơn
    boolean hasEnoughBalance(Long recyclerId, BigDecimal amount);
}
