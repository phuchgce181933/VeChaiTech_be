package com.ra.base_spring_boot.services.impl;


import com.ra.base_spring_boot.model.Orders;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements IWalletService {
    private final IDepositTransactionRepository depositRepo;
    private final IWalletRecyclerRepository walletRepo;
    private final IWalletTransactionRepository transactionRepo;
    @Override
    public void depositSuccess(Long orderCode) {
        WalletDepositTransaction deposit =
                depositRepo.findByOrderCode(orderCode)
                        .orElseThrow(() ->
                                new RuntimeException("Deposit not found, retry webhook"));

        if (deposit.getStatus() == DepositStatus.SUCCESS) {
            return;
        }

        WalletRecycler wallet = deposit.getWallet();
        BigDecimal amount = deposit.getAmount();

        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setTotalDeposited(wallet.getTotalDeposited().add(amount));

        deposit.setStatus(DepositStatus.SUCCESS);

        WalletTransaction tx = WalletTransaction.builder()
                .wallet(wallet)
                .type("DEPOSIT")
                .amount(amount)
                .status("SUCCESS")
                .orderCode(orderCode)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepo.save(tx);
    }


    @Override
    public BigDecimal getBalance(Long recyclerId) {
        return walletRepo.findByRecyclerId(recyclerId)
                .map(WalletRecycler::getBalance)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public List<WalletTransaction> getTransactions(Long recyclerId) {
        return transactionRepo
                .findByWallet_Recycler_IdOrderByTransactionDateDesc(recyclerId);
    }

}
