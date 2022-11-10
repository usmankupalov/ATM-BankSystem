package com.example.atmbanksystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Integer bankId;

    @Column(name = "bank_name", unique = true)
    private String bankName;

    @Column(name = "invoice_bank", unique = true)
    private Integer invoiceOfBank;

    public Bank(){}

    public Bank(String bankName, Integer invoiceOfBank) {
        this.bankName = bankName;
        this.invoiceOfBank = invoiceOfBank;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getInvoiceOfBank() {
        return invoiceOfBank;
    }

    public void setInvoiceOfBank(Integer invoiceOfBank) {
        this.invoiceOfBank = invoiceOfBank;
    }

    @Override
    public String toString() {
        return "Bank{" +
                " bankId='" + bankId + '\'' +
                ", nameOfBank='" + bankName + '\'' +
                ", invoice='" + invoiceOfBank + '\'' +
                '}';
    }
}
