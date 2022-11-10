package com.example.atmbanksystem.service;

import com.example.atmbanksystem.entity.Bank;

import java.util.List;

public interface BankService {
    List<Bank> getAllBanks();
    Bank findBankByName(String name);
}
