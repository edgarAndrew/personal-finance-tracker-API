package com.edgarAndrew.PersonalFinanceTracker.repositories;

import com.edgarAndrew.PersonalFinanceTracker.models.BankAccount;
import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    Boolean existsByAccountNumber(String accountNumber);
    List<BankAccount>  findByUser(User user);
}
