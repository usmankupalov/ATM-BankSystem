package com.example.atmbanksystem.service.impl;

import com.example.atmbanksystem.entity.Bank;
import com.example.atmbanksystem.service.BankService;

import java.util.List;

public class BankServiceImpl implements BankService {

    @Override
    public List<Bank> getAllBanks() {
        return Transaction.of(session -> {
            List<Bank> banks = session.createQuery(
                    "select b " +
                            "from Bank b", Bank.class)
                    .getResultList();
            return banks;
        }).run();
    }

    @Override
    public Bank findBankByName(String name) {
        return Transaction.of(session -> {
            Bank bank = session.createQuery(
                    "select b " +
                            "from Bank b " +
                            "where b.bankName=:name", Bank.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return bank;
        }).run();
    }
}
