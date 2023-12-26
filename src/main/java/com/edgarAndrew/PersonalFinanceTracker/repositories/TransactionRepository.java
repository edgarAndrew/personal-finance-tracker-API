package com.edgarAndrew.PersonalFinanceTracker.repositories;

import com.edgarAndrew.PersonalFinanceTracker.models.Transaction;
import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Transaction> findByBankAccountUserAndDateBetween(User user, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Transaction> findByBankAccountUser(User user);
    Page<Transaction> findTransactionByBankAccountUser(@Param("user") User user, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.bankAccount.user = :user " +
            "AND t.date BETWEEN :startDateTime AND :endDateTime " +
            "ORDER BY t.date DESC")
    Page<Transaction> findTransactionsByUserAndDateBetween(
            @Param("user") User user,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            Pageable pageable
    );
}
