package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.WalletDepositTransaction;
import com.ra.base_spring_boot.model.constants.DepositStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IWalletDepositTransactionRepository  extends JpaRepository<WalletDepositTransaction, Long> {
    @Query("""
        SELECT COALESCE(SUM(w.amount), 0)
        FROM WalletDepositTransaction w
        WHERE w.status = :status
        AND w.createdAt BETWEEN :start AND :end
    """)
    BigDecimal sumRevenueByTime(
            DepositStatus status,
            LocalDateTime start,
            LocalDateTime end
    );
    @Query("""
        SELECT COALESCE(SUM(w.amount), 0)
        FROM WalletDepositTransaction w
        WHERE w.status = :status
        AND w.createdAt BETWEEN :start AND :end
    """)
    BigDecimal sumRevenueByWeek(
            @Param("status") DepositStatus status,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
