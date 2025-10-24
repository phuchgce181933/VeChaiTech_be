package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.model.Transactions;
import com.ra.base_spring_boot.repository.ITransactionsRepository;
import com.ra.base_spring_boot.services.ITransactionsService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TransactionsServiceImpl implements ITransactionsService {

    private final ITransactionsRepository iTransactionsRepository;

    public TransactionsServiceImpl(ITransactionsRepository transactionsRepository) {
        this.iTransactionsRepository = transactionsRepository;
    }

    @Override
    public Transactions saveTransaction(Transactions transaction) {
        return iTransactionsRepository.save(transaction);
    }

    @Override
    public Transactions updateTransaction(Long id, Transactions transaction) {
        Optional<Transactions> existing = iTransactionsRepository.findById(id);
        if (existing.isPresent()) {
            Transactions t = existing.get();
            t.setLatitude(transaction.getLatitude());
            t.setLongitude(transaction.getLongitude());
            return iTransactionsRepository.save(t);
        } else {
            throw new RuntimeException("Transaction not found with id " + id);
        }
    }

    @Override
    public void deleteTransaction(Long id) {
        iTransactionsRepository.deleteById(id);
    }

    @Override
    public Optional<Transactions> getTransactionById(Long id) {
        return iTransactionsRepository.findById(id);
    }

    @Override
    public List<Transactions> getAllTransactions() {
        return iTransactionsRepository.findAll();
    }

    @Override
    public Transactions getLatestTransaction() {
        return iTransactionsRepository.findTopByOrderByIdDesc()
                .orElse(null);
    }
}
