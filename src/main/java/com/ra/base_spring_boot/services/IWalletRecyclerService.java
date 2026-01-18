package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.WalletDepositRequest;
import com.ra.base_spring_boot.dto.req.WalletWithdrawRequest;
import com.ra.base_spring_boot.dto.resp.WalletRecyclerResponse;
import com.ra.base_spring_boot.dto.resp.WalletTransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface IWalletRecyclerService {
    
    // Quản lý ví
    WalletRecyclerResponse getWalletByRecyclerId(Long recyclerId);
    
    WalletRecyclerResponse createWallet(Long recyclerId);
    
    // Nạp tiền
    WalletTransactionResponse deposit(WalletDepositRequest request);
    
    // Rút tiền
    WalletTransactionResponse withdraw(WalletWithdrawRequest request);
    
    // Xem số dư
    BigDecimal getBalance(Long recyclerId);
    
    // Thanh toán để nhận đơn
    WalletTransactionResponse payForOrder(Long recyclerId, Long orderId, BigDecimal amount);
    
    // Hoàn tiền
    WalletTransactionResponse refund(Long recyclerId, Long orderId, BigDecimal amount);
    
    // Lịch sử giao dịch
    List<WalletTransactionResponse> getTransactionHistory(Long recyclerId);
    
    List<WalletTransactionResponse> getTransactionsByType(Long recyclerId, String type);
    
    // Kiểm tra số dư đủ để nhận đơn
    boolean hasEnoughBalance(Long recyclerId, BigDecimal amount);
}
