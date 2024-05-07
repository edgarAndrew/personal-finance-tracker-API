package com.edgarAndrew.PersonalFinanceTracker.services;

import com.edgarAndrew.PersonalFinanceTracker.exceptions.goal.GoalCompletedException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.goal.GoalNotFoundException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.user.AuthorizationException;
import com.edgarAndrew.PersonalFinanceTracker.models.Goal;
import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import com.edgarAndrew.PersonalFinanceTracker.repositories.GoalRepository;
import com.edgarAndrew.PersonalFinanceTracker.repositories.UserRepository;
import com.edgarAndrew.PersonalFinanceTracker.DTO.goal.UpdateGoalRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.time.LocalDate;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> getAllGoalsByUser() {
        // Retrieve the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // Fetch all goals associated with the user
        return goalRepository.findByUser(user);
    }

    @Transactional
    public Goal addGoal(String name, double targetAmount, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            throw new IllegalArgumentException("Start date must be before the end date");
        }

        // User Id from jwt token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Goal goal = new Goal(name, targetAmount, startDate, endDate, user);
        return goalRepository.save(goal);
    }

    @Transactional
    public void removeGoal(Long goalId){
        Goal goal = this.getGoalById(goalId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(!goal.getUser().getId().equals(user.getId()))
            throw new AuthorizationException("Goal with id " + goalId + "does not belong to you");

        goalRepository.deleteById(goalId);
    }

    private Goal getGoalById(Long goalId) {
        return goalRepository.findById(goalId).orElseThrow(()-> new GoalNotFoundException("No Goal with id: + " + goalId));
    }

    @Transactional
    public void addMoneyToGoal(Long goalId, double amountToAdd) {
        Goal goal = getGoalById(goalId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(!goal.getUser().getId().equals(user.getId()))
            throw new AuthorizationException("Goal with id " + goalId + "does not belong to you");


        if (goal.getCurrentAmount() >= goal.getTargetAmount()) {
            throw new GoalCompletedException("Goal with id " + goalId + " is already completed");
        }

        double newAmount = goal.getCurrentAmount() + amountToAdd;
        goal.setCurrentAmount(newAmount);
        goalRepository.save(goal);
    }

    @Transactional
    public void updateGoal(Long goalId, UpdateGoalRequest updateGoalRequest) {
        Goal goal = getGoalById(goalId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(!goal.getUser().getId().equals(user.getId()))
            throw new AuthorizationException("Goal with id " + goalId + "does not belong to you");

        if (updateGoalRequest.getName() != null) {
            goal.setName(updateGoalRequest.getName());
        }
        if (updateGoalRequest.getTargetAmount() != null) {
            goal.setTargetAmount(updateGoalRequest.getTargetAmount());
        }
        if (updateGoalRequest.getEndDate() != null) {
            goal.setEndDate(updateGoalRequest.getEndDate());
        }

        goalRepository.save(goal);
    }

}
