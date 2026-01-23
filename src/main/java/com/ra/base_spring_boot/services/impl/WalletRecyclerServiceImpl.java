package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.WalletDepositRequest;
import com.ra.base_spring_boot.dto.req.WalletWithdrawRequest;
import com.ra.base_spring_boot.dto.resp.WalletRecyclerResponse;
import com.ra.base_spring_boot.dto.resp.WalletTransactionResponse;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.model.Orders;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.model.WalletRecycler;
import com.ra.base_spring_boot.model.WalletTransaction;
import com.ra.base_spring_boot.repository.IOrdersRepository;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.repository.IWalletRecyclerRepository;
import com.ra.base_spring_boot.repository.IWalletTransactionRepository;
import com.ra.base_spring_boot.services.IWalletRecyclerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletRecyclerServiceImpl implements IWalletRecyclerService {

    private final IWalletRecyclerRepository walletRepository;
    private final IWalletTransactionRepository transactionRepository;
    private final IUserRepository userRepository;
    private final IOrdersRepository ordersRepository;

    @Override
    public WalletTransactionResponse payForOrder(Long recyclerId, Long orderId, BigDecimal amount) {
        WalletRecycler wallet = walletRepository.findByRecyclerId(recyclerId)
                .orElseThrow(() -> new HttpNotFound("Wallet not found"));

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new HttpNotFound("Order not found"));

        if (!wallet.getIsActive()) {
            throw new HttpBadRequest("Wallet is inactive");
        }

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new HttpBadRequest("nạp tiền để có thể nhận đơn to access this order. Required: " + amount + " VND");
        }

        // Trừ tiền
        BigDecimal newBalance = wallet.getBalance().subtract(amount);
        wallet.setBalance(newBalance);
        wallet.setTotalSpent(wallet.getTotalSpent().add(amount));
        walletRepository.save(wallet);

        // Tạo ghi nhận giao dịch
        WalletTransaction transaction = WalletTransaction.builder()
                .wallet(wallet)
                .type("PAYMENT")
                .amount(amount)
                .description("Thanh toán để nhận đơn #" + orderId)
                .transactionDate(LocalDateTime.now())
                .relatedOrder(order)
                .status("SUCCESS")
                .balanceAfter(newBalance)
                .build();

        WalletTransaction savedTransaction = transactionRepository.save(transaction);
        return convertTransactionToResponse(savedTransaction);
    }

    @Override
    public boolean hasEnoughBalance(Long recyclerId, BigDecimal amount) {
        try {
            WalletRecycler wallet = walletRepository.findByRecyclerId(recyclerId)
                    .orElse(null);

            if (wallet == null) {
                return false;
            }

            return wallet.getBalance().compareTo(amount) >= 0 && wallet.getIsActive();
        } catch (Exception e) {
            return false;
        }
    }

    private WalletRecyclerResponse convertToResponse(WalletRecycler wallet) {
        return WalletRecyclerResponse.builder()
                .id(wallet.getId())
                .recyclerId(wallet.getRecycler() != null ? wallet.getRecycler().getId() : null)
                .recyclerName(wallet.getRecycler() != null ? wallet.getRecycler().getFullName() : null)
                .balance(wallet.getBalance())
                .totalDeposited(wallet.getTotalDeposited())
                .totalSpent(wallet.getTotalSpent())
                .isActive(wallet.getIsActive())
                .build();
    }

    private WalletTransactionResponse convertTransactionToResponse(WalletTransaction transaction) {
        return WalletTransactionResponse.builder()
                .id(transaction.getId())
                .walletId(transaction.getWallet() != null ? transaction.getWallet().getId() : null)
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .relatedOrderId(transaction.getRelatedOrder() != null ? transaction.getRelatedOrder().getId() : null)
                .status(transaction.getStatus())
                .balanceAfter(transaction.getBalanceAfter())
                .paymentMethod(transaction.getPaymentMethod())
                .referenceCode(transaction.getReferenceCode())
                .build();
    }
}
