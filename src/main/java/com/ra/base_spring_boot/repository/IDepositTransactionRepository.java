package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.WalletDepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDepositTransactionRepository extends JpaRepository<WalletDepositTransaction, Long> {
    Optional<WalletDepositTransaction> findByOrderCode(Long orderCode);
}
