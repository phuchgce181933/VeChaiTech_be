package com.ra.base_spring_boot.repository;



import com.ra.base_spring_boot.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ITransactionsRepository extends JpaRepository<Transactions, Long> {
    Optional<Transactions> findTopByOrderByIdDesc();
}
