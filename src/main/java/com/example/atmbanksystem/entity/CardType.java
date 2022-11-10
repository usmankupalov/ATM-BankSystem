package com.example.atmbanksystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "type_card")
public class CardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_type_id")
    private Integer cardTypeId;

    @Column(name = "card_type", unique = true)
    private String cardType;

    public CardType() {}

    public CardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(Integer cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "CardType{" +
                "cardTypeId=" + cardTypeId +
                ", cardType='" + cardType + '\'' +
                '}';
    }
}
