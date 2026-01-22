package com.ra.base_spring_boot.services.impl;


import com.ra.base_spring_boot.model.WalletDepositTransaction;
import com.ra.base_spring_boot.model.WalletRecycler;
import com.ra.base_spring_boot.model.constants.DepositStatus;
import com.ra.base_spring_boot.repository.IDepositTransactionRepository;
import com.ra.base_spring_boot.repository.IWalletRecyclerRepository;
import com.ra.base_spring_boot.services.IWalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements IWalletService {
    private final IDepositTransactionRepository depositRepo;
    private final IWalletRecyclerRepository walletRepo;
    @Override
    public void depositSuccess(Long orderCode) {
        WalletDepositTransaction tx = depositRepo.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (tx.getStatus() == DepositStatus.SUCCESS) return;

        WalletRecycler wallet = tx.getWallet();

        wallet.setBalance(wallet.getBalance().add(tx.getAmount()));
        wallet.setTotalDeposited(wallet.getTotalDeposited().add(tx.getAmount()));

        tx.setStatus(DepositStatus.SUCCESS);

        walletRepo.save(wallet);
        depositRepo.save(tx);
    }
}
