package com.edgarAndrew.PersonalFinanceTracker.repositories;

import com.edgarAndrew.PersonalFinanceTracker.models.BankAccount;
import com.edgarAndrew.PersonalFinanceTracker.models.Budget;
import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Budget b " +
            "WHERE b.user = :user " +
            "AND b.startDate <= :endDate " +
            "AND b.endDate >= :startDate")
    boolean existsBudgetForDateRange(@Param("user") User user,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM Budget b " +
            "WHERE b.user = :user " +
            "AND b.startDate <= :currentDate " +
            "AND b.endDate >= :currentDate")
    Optional<Budget> findBudgetForCurrentMonth(@Param("user") User user, @Param("currentDate") LocalDate currentDate);
    List<Budget>  findByUser(User user);
}
