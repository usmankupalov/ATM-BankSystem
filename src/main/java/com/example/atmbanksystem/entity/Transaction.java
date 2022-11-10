package com.example.atmbanksystem.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "amount_money")
    private double amountMoney;

    @Column(name = "time_of_transaction")
    private Date timeOfTransaction;

    @Column(name = "memo")
    private String memo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_card_id")
    private Card fromCard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_card_id")
    private Card toCard;

    public Transaction() {}

    public Transaction(double amountMoney, Date timeOfTransaction, String memo, Card fromCard, Card toCard) {
        this.amountMoney = amountMoney;
        this.timeOfTransaction = timeOfTransaction;
        this.memo = memo;
        this.toCard = fromCard;
        this.fromCard = fromCard;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(double amountMoney) {
        this.amountMoney = amountMoney;
    }

    public Date getTimeOfTransaction() {
        return timeOfTransaction;
    }

    public void setTimeOfTransaction(Date timeOfTransaction) {
        this.timeOfTransaction = timeOfTransaction;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Card getFromCard() {
        return fromCard;
    }

    public void setFromCard(Card fromCard) {
        this.fromCard = fromCard;
    }

    public Card getToCard() {
        return toCard;
    }

    public void setToCard(Card toCard) {
        this.toCard = toCard;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amountMoney=" + amountMoney +
                ", timeOfTransaction=" + timeOfTransaction +
                ", memo='" + memo + '\'' +
                ", fromCard='" + "cardType=" + fromCard.getCardType().getCardType() + '\'' + " bankName=" + fromCard.getBank().getBankName() +
                " " + '\'' + "pinNumber=" + fromCard.getPinNumber() + '\'' +  "firstname=" + fromCard.getUser().getFirstname() + '\'' +
                ", toCard='" + "cardType=" + toCard.getCardType().getCardType() + '\'' + " bankName=" + fromCard.getBank().getBankName() +
                " " + '\'' + "pinNumber=" + toCard.getPinNumber() + '\'' + " firstname=" + toCard.getUser().getFirstname() + '\'' +
                '}';
    }
}
