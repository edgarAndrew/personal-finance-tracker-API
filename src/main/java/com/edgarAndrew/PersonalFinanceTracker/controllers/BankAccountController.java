package com.edgarAndrew.PersonalFinanceTracker.controllers;

import com.edgarAndrew.PersonalFinanceTracker.DTO.bank.GetBankAccountsResponse;
import com.edgarAndrew.PersonalFinanceTracker.models.BankAccount;
import com.edgarAndrew.PersonalFinanceTracker.services.BankAccountService;
import com.edgarAndrew.PersonalFinanceTracker.DTO.bank.AddBankAccountRequest;
import com.edgarAndrew.PersonalFinanceTracker.DTO.bank.AddBankAccountResponse;
import com.edgarAndrew.PersonalFinanceTracker.DTO.bank.RemoveBankAccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bank-accounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public ResponseEntity<?> addBankAccount(@RequestBody AddBankAccountRequest bankAccountRequest) {

            BankAccount bankAccount = bankAccountService.addBankAccount(
                    bankAccountRequest.getAccountNumber(),
                    bankAccountRequest.getInitialBalance()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(new AddBankAccountResponse("Account added"));
    }

    @DeleteMapping
    public ResponseEntity<?> removeBankAccount(@RequestParam(name = "id") Long id){
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RemoveBankAccountResponse("Account has been removed"));
    }

    @GetMapping("/my-accounts")
    public ResponseEntity<?> getMyBankAccounts(){
        List<GetBankAccountsResponse> bankAccounts = bankAccountService.getMyBankAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(bankAccounts);
    }

}
