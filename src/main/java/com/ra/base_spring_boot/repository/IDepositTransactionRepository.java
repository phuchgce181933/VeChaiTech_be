package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.WalletDepositTransaction;
import com.ra.base_spring_boot.model.constants.DepositStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IDepositTransactionRepository extends JpaRepository<WalletDepositTransaction, Long> {
    
    @Query("SELECT d FROM WalletDepositTransaction d LEFT JOIN FETCH d.wallet WHERE d.orderCode = :orderCode")
    Optional<WalletDepositTransaction> findByOrderCode(@Param("orderCode") Long orderCode);
    
    List<WalletDepositTransaction> findByRecyclerIdAndStatus(Long recyclerId, DepositStatus status);
    
    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM WalletDepositTransaction d WHERE d.recyclerId = :recyclerId AND d.status = :status")
    BigDecimal sumAmountByRecyclerIdAndStatus(@Param("recyclerId") Long recyclerId, @Param("status") DepositStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    select w
    from WalletDepositTransaction w
    where w.orderCode = :orderCode
""")
    Optional<WalletDepositTransaction> lockByOrderCode(@Param("orderCode") Long orderCode);

}
