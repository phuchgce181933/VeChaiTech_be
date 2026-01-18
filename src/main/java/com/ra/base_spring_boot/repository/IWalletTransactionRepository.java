package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    
    List<WalletTransaction> findByWalletId(Long walletId);
    
    List<WalletTransaction> findByWalletIdOrderByTransactionDateDesc(Long walletId);
    
    List<WalletTransaction> findByWalletIdAndType(Long walletId, String type);
    
    List<WalletTransaction> findByWalletIdAndStatus(Long walletId, String status);
}
