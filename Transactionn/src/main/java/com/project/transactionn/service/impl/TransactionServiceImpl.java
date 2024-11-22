package com.project.transactionn.service.impl;

import com.project.transactionn.model.Transaction;
import com.project.transactionn.repository.TransactionRepository;
import com.project.transactionn.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        transaction.setReferenceGroupId(generateReferenceGroupId());
        transaction.setStatus("PENDING");
        return transactionRepository.save(transaction);
    }
    @Override
    public Transaction updateTansaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }
    @Override
    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    private static String generateReferenceGroupId() {
        return "REF-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }

}
