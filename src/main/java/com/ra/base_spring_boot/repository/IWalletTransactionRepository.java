package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IWalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findByWallet_IdOrderByTransactionDateDesc(Long walletId);
    
    Optional<WalletTransaction> findByOrderCode(Long orderCode);

    List<WalletTransaction> findByWallet_Recycler_IdOrderByTransactionDateDesc(Long recyclerId);

}
