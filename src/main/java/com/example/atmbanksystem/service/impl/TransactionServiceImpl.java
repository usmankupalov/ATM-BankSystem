package com.example.atmbanksystem.service.impl;

import com.example.atmbanksystem.entity.Transaction;
import com.example.atmbanksystem.service.TransactionService;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    @Override
    public List<Transaction> getAllTransactionOfOneCard(Integer cardId) {
        return com.example.atmbanksystem.service.impl.Transaction.of(session -> {
            List<Transaction> transactions = session.createQuery(
                    "select t " +
                            "from Transaction t " +
                            "where t.fromCard.cardId=:cardId", Transaction.class)
                    .setParameter("cardId", cardId)
                    .getResultList();
            return transactions;
        }).run();
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        com.example.atmbanksystem.service.impl.Transaction.of(session -> {
            session.save(transaction);
            return transaction;
        }).run();
    }
}
