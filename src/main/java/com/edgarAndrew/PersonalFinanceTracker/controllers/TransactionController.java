package com.edgarAndrew.PersonalFinanceTracker.controllers;

import com.edgarAndrew.PersonalFinanceTracker.DTO.transaction.GetTransactionResponse;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction.GetTransactionByCategoryResponse;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction.InvalidDateCombinationException;
import com.edgarAndrew.PersonalFinanceTracker.models.Transaction;
import com.edgarAndrew.PersonalFinanceTracker.services.TransactionService;
import com.edgarAndrew.PersonalFinanceTracker.DTO.transaction.AddTransactionRequest;
import com.edgarAndrew.PersonalFinanceTracker.DTO.transaction.AddTransactionResponse;
import com.edgarAndrew.PersonalFinanceTracker.DTO.transaction.RemoveTransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody AddTransactionRequest addTransactionRequest) {
        Transaction transaction = transactionService.addTransaction(
                    addTransactionRequest.getBankAccountId(),
                    addTransactionRequest.getAmount(),
                    addTransactionRequest.getType(),
                    addTransactionRequest.getCategory(),
                    addTransactionRequest.getBudget()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new AddTransactionResponse(transaction.getBankAccount().getAccountNumber(),"Transaction added")
            );
    }

    @DeleteMapping
    public ResponseEntity<?> removeTransaction(@RequestParam(name = "id") Long id){
        transactionService.removeTransaction(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RemoveTransactionResponse("Transaction has been removed"));
    }

    @GetMapping("/all-categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = transactionService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping
    public ResponseEntity<?> getTransactions(
            @RequestParam(name="day",required = false) Integer day,
            @RequestParam(name="month",required = false) Integer month,
            @RequestParam(name="year",required = false) Integer year,
            @RequestParam(name="category",required = false, defaultValue = "false") boolean category,
            @RequestParam(name="type",required = false,defaultValue = "BOTH") String type,
            @RequestParam(name ="page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        LocalDate startDate = null;
        LocalDate endDate=null;
        Pageable p = PageRequest.of(page,size);

        if(day == null && month == null && year == null) {
            // give all transactions
            return ResponseEntity.ok(transactionService.getAllTransactions(p));
        }

        else if (day != null && month!=null && year !=null) {
            // give transactions for that day
            startDate = LocalDate.of(year, month, day);
            endDate = startDate;
        }

        else if (month != null && year != null ) {
            // give transactions for that month
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        }

        else if (day == null && month == null && year != null) {
            // give transactions for that year
            startDate = LocalDate.of(year, 1, 1);
            endDate = startDate.withDayOfYear(startDate.lengthOfYear());
        }

        else{
            // throw error like could not understand the request, or improper dates
            throw new InvalidDateCombinationException("Date combinations provided are ambiguous in nature");
        }

        if(!category){
            Page<GetTransactionResponse> transactions = transactionService.getTransactions(startDate, endDate,type,p);
            return ResponseEntity.status(HttpStatus.OK).body(transactions);
        }
        else {
            List<GetTransactionByCategoryResponse> transactions = transactionService.getCategoryPercentageAndAmount(startDate, endDate,type);
            return ResponseEntity.status(HttpStatus.OK).body(transactions);
        }
    }

}
