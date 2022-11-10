package com.example.atmbanksystem.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer cardId;

    @Column(name = "password_of_card")
    private Integer passwordOfCard;

    @Column(name = "pin_number", unique = true)
    private Integer pinNumber;

    @Column(name = "amount_money", precision = 0)
    private double amountMoney;

    @Column(name = "date_created")
    private Date dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_type_id")
    private CardType cardType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    public Card(){}

    public Card(Integer passwordOfCard, Integer pinNumber, double amountMoney, Date dateCreated, CardType cardType, Bank bank) {
        this.passwordOfCard = passwordOfCard;
        this.pinNumber = pinNumber;
        this.amountMoney = amountMoney;
        this.dateCreated = dateCreated;
        this.cardType = cardType;
        this.bank = bank;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getPasswordOfCard() {
        return passwordOfCard;
    }

    public void setPasswordOfCard(Integer passwordOfCard) {
        this.passwordOfCard = passwordOfCard;
    }

    public Integer getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(Integer pinNumber) {
        this.pinNumber = pinNumber;
    }

    public double getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(double amountMoney) {
        this.amountMoney = amountMoney;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", passwordOfCard='" + passwordOfCard + '\'' +
                ", pinNumber=" + pinNumber +
                ", amountMoney=" + amountMoney +
                ", dateCreated=" + dateCreated +
                ", cardType=" + cardType +
                ", user=" + user +
                ", bank=" + bank +
                '}';
    }
}
