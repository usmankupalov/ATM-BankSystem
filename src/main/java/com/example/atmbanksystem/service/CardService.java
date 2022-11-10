package com.example.atmbanksystem.service;

import com.example.atmbanksystem.entity.Card;

import java.util.List;

public interface CardService {
    void createCard(Card card);
    Card findCardByPinNumber(Integer pinNumber);
    Card findCardByPinNumberAndPassword(Integer pinNumber, Integer password);
    List<Card> getAllCards();
    void updateCardPassword(Integer cardId, Integer newPassword);
    void updateCardSumMoney(Integer cardId, double amountMoney);
}
