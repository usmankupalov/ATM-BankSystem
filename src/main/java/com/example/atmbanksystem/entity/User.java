package com.example.atmbanksystem.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_Id")
    private Integer userId;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "pin_number_passport", unique = true)
    private Integer pinNumberOfPassport;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Card> cards;

    public User(){}

    public User(String firstname, String lastname, Integer pinNumberOfPassport) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pinNumberOfPassport = pinNumberOfPassport;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getPinNumberOfPassport() {
        return pinNumberOfPassport;
    }

    public void setPinNumberOfPassport(Integer pinNumberOfPassport) {
        this.pinNumberOfPassport = pinNumberOfPassport;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", pinNumberOfPassport='" + pinNumberOfPassport + '\'' +
                '}';
    }
}
