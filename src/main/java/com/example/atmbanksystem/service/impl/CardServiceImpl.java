package com.example.atmbanksystem.service.impl;

import com.example.atmbanksystem.entity.Card;
import com.example.atmbanksystem.service.CardService;

import java.util.List;

public class CardServiceImpl implements CardService {

    @Override
    public void createCard(Card card) {
        Transaction.of(session -> {
            session.save(card);
            return card;
        }).run();
    }

    @Override
    public Card findCardByPinNumber(Integer pinNumber) {
        return Transaction.of(session -> {
            Card card = session.createQuery(
                    "select c " +
                            "from Card c " +
                            "where c.pinNumber=:pinNumber", Card.class)
                    .setParameter("pinNumber", pinNumber)
                    .getSingleResult();
            return card;
        }).run();
    }

    @Override
    public Card findCardByPinNumberAndPassword(Integer pinNumber, Integer password) {
        return Transaction.of(session -> {
            Card card = session.createQuery(
                    "select c " +
                            "from Card c " +
                            "where c.pinNumber=:pinNumber and c.passwordOfCard=:password", Card.class)
                    .setParameter("pinNumber", pinNumber)
                    .setParameter("password", password)
                    .getSingleResult();
            return card;
        }).run();
    }

    @Override
    public List<Card> getAllCards() {
        return Transaction.of(session -> {
            List<Card> cards = session.createQuery(
                    "select c " +
                            "from Card c ", Card.class)
                    .getResultList();
            return cards;
        }).run();
    }

    @Override
    public void updateCardPassword(Integer cardId, Integer newPassword) {
        Transaction.of(session -> {
            Card card = session.get(Card.class, cardId);
            card.setPasswordOfCard(newPassword);
            session.update(card);
            return card;
        }).run();
    }

    @Override
    public void updateCardSumMoney(Integer cardId, double amountMoney) {
        Transaction.of(session -> {
            Card card = session.get(Card.class, cardId);
            card.setAmountMoney(amountMoney);
            session.update(card);
            return card;
        }).run();
    }
}
