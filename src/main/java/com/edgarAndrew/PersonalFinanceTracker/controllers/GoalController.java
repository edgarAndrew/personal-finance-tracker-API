package com.edgarAndrew.PersonalFinanceTracker.controllers;

import com.edgarAndrew.PersonalFinanceTracker.models.Goal;
import com.edgarAndrew.PersonalFinanceTracker.services.GoalService;
import com.edgarAndrew.PersonalFinanceTracker.DTO.goal.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<?> addGoal(@RequestBody AddGoalRequest addGoalRequest) {
            Goal goal = goalService.addGoal(
                    addGoalRequest.getName(),
                    addGoalRequest.getTargetAmount(),
                    addGoalRequest.getStartDate(),
                    addGoalRequest.getEndDate()
            );
        return ResponseEntity.status(HttpStatus.CREATED).body(new AddGoalResponse("Goal Added"));
    }

    @DeleteMapping
    public ResponseEntity<?> removeGoal(@RequestParam(name = "id") Long id){
        goalService.removeGoal(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RemoveGoalResponse("Goal has been removed"));
    }

    @PutMapping
    public ResponseEntity<?> updateGoal(@RequestParam(name = "id") Long id,@RequestBody UpdateGoalRequest updateGoalRequest){
        goalService.updateGoal(id,updateGoalRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateGoalResponse("Goal has been updated"));
    }

    @PatchMapping("/add-money")
    public ResponseEntity<?> addMoneyToGoal(@RequestBody AddMoneyRequest addMoneyRequest) {
        goalService.addMoneyToGoal(
                addMoneyRequest.getGoalId(),
                addMoneyRequest.getAmount()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new AddMoneyResponse("Money added to goal"));
    }

}
