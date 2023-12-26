package com.edgarAndrew.PersonalFinanceTracker.repositories;

import com.edgarAndrew.PersonalFinanceTracker.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal,Long> {
}
