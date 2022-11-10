package com.example.atmbanksystem.service.impl;

import com.example.atmbanksystem.entity.CardType;
import com.example.atmbanksystem.service.CardTypeService;

public class CardTypeServiceImpl implements CardTypeService {

    @Override
    public CardType getCardTypeByName(String cardName) {
        return Transaction.of(session -> {
            CardType cardType = session.createQuery(
                    "select ct " +
                            "from CardType ct " +
                            "where ct.cardType=:cardName", CardType.class)
                    .setParameter("cardName", cardName)
                    .getSingleResult();
            return cardType;
        }).run();
    }
}
