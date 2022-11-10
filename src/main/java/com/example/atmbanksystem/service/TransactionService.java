package com.example.atmbanksystem.service;

import com.example.atmbanksystem.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactionOfOneCard(Integer cardId);
    void saveTransaction(Transaction transaction);
}
