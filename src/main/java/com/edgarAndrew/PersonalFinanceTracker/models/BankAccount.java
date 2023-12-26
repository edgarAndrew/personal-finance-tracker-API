package com.edgarAndrew.PersonalFinanceTracker.models;

import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "bank_accounts", uniqueConstraints = @UniqueConstraint(columnNames = "accountNumber"))
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false,unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private double balance;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public BankAccount() {
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", user=" + user +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }

    public BankAccount(User user, String accountNumber, double balance) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
