package com.edgarAndrew.PersonalFinanceTracker.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION CHECK (amount > 0)")
    private double amount;

    @Column(nullable = false, columnDefinition = "VARCHAR(10) CHECK (type IN ('INCOME', 'EXPENSE'))")
    private String type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private BankAccount bankAccount;

    public Transaction() {
    }

    public Transaction(double amount, String type, Category category, LocalDateTime date, BankAccount bankAccount) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.bankAccount = bankAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public enum Category {
        FOOD, EDUCATION, SALARY,
        SPORT, TRIPS, PETS,
        HOME,GROCERIES,CHARITY,
        BONUS,CLOTHES,VEHICLE,
        DRINKS,ELECTRONICS,KIDS,
        HEALTH,GIFT,GAMES,CINEMA,
        HOBBY,BEAUTY,TAXES,INVESTMENT
    }
}

