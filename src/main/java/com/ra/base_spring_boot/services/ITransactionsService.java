package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.model.Transactions;

import java.util.List;
import java.util.Optional;

public interface ITransactionsService {
    Transactions saveTransaction(Transactions transaction);
    Transactions updateTransaction(Long id, Transactions transaction);
    void deleteTransaction(Long id);
    Optional<Transactions> getTransactionById(Long id);
    List<Transactions> getAllTransactions();
    Transactions getLatestTransaction();
}
