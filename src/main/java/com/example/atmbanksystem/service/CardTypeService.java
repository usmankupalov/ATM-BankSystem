package com.example.atmbanksystem.service;

import com.example.atmbanksystem.entity.CardType;

public interface CardTypeService {
    CardType getCardTypeByName(String cardName);
}
