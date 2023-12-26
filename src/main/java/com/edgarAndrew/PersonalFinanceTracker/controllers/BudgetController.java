package com.edgarAndrew.PersonalFinanceTracker.controllers;

import com.edgarAndrew.PersonalFinanceTracker.models.Budget;
import com.edgarAndrew.PersonalFinanceTracker.services.BudgetService;
import com.edgarAndrew.PersonalFinanceTracker.DTO.budget.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<?> addBudget(@RequestBody AddBudgetRequest addBudgetRequest) {

        Budget budget = budgetService.addBudget(
                addBudgetRequest.getName(),
                addBudgetRequest.getStartAmount()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new AddBudgetResponse("Budget Added"));
    }

    @DeleteMapping
    public ResponseEntity<?> removeBudget(@RequestParam(name = "id") Long id){
        budgetService.removeBudget(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RemoveBudgetResponse("Budget has been removed"));
    }

    @PutMapping
    public ResponseEntity<?> updateBudget(@RequestParam(name = "id") Long id, @RequestBody UpdateBudgetRequest updateBudgetRequest){
        budgetService.updateBudget(id,updateBudgetRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateBudgetResponse("Budget updated"));
    }

}
