package com.ra.base_spring_boot.services.impl;


import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.model.WalletDepositTransaction;
import com.ra.base_spring_boot.model.WalletRecycler;
import com.ra.base_spring_boot.model.WalletTransaction;
import com.ra.base_spring_boot.model.constants.DepositStatus;
import com.ra.base_spring_boot.repository.IDepositTransactionRepository;
import com.ra.base_spring_boot.repository.IWalletRecyclerRepository;
import com.ra.base_spring_boot.repository.IWalletTransactionRepository;
import com.ra.base_spring_boot.services.IWalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements IWalletService {
    private final IDepositTransactionRepository depositRepo;
    private final IWalletRecyclerRepository walletRepo;
    private final IWalletTransactionRepository walletTransactionRepo;
    private final IWalletRecyclerRepository walletRecyclerRepository;

    @Override
    @Transactional
    public void handleDepositSuccess(Long orderCode) {

        WalletDepositTransaction deposit = depositRepo.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Deposit not found"));

        if (deposit.getStatus() == DepositStatus.SUCCESS) return;

        Long recyclerId = deposit.getRecyclerId();
        BigDecimal amount = deposit.getAmount();

        // ✅ LẤY HOẶC TẠO WALLET
        WalletRecycler wallet = walletRepo.findByRecyclerId(recyclerId)
                .orElseGet(() -> walletRepo.save(new WalletRecycler(recyclerId)));

        // ✅ CỘNG TIỀN
        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setTotalDeposited(wallet.getTotalDeposited().add(amount));

        // update deposit
        deposit.setStatus(DepositStatus.SUCCESS);
        deposit.setWallet(wallet);

        // log transaction
        walletTransactionRepo.save(
                WalletTransaction.builder()
                        .wallet(wallet)
                        .type("DEPOSIT")
                        .amount(amount)
                        .status("SUCCESS")
                        .balanceAfter(wallet.getBalance())
                        .orderCode(orderCode)
                        .paymentMethod("PAYOS")
                        .description("Nạp tiền PayOS")
                        .transactionDate(LocalDateTime.now())
                        .build()
        );

        walletRepo.save(wallet);
        depositRepo.save(deposit);

        System.out.println("✅ WALLET UPDATED recycler=" + recyclerId + " +" + amount);
    }

}
