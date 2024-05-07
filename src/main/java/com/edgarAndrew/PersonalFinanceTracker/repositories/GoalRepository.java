package com.edgarAndrew.PersonalFinanceTracker.repositories;

import com.edgarAndrew.PersonalFinanceTracker.models.Goal;
import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal,Long> {
    List<Goal> findByUser(User user);
}
