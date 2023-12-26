package com.edgarAndrew.PersonalFinanceTracker.services;

import com.edgarAndrew.PersonalFinanceTracker.DTO.bank.GetBankAccountsResponse;
import com.edgarAndrew.PersonalFinanceTracker.DTO.transaction.GetTransactionResponse;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.bank.BankNotFoundException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.bank.DuplicateAccountNumberException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.bank.InsufficientFundsException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.user.AuthorizationException;
import com.edgarAndrew.PersonalFinanceTracker.models.BankAccount;
import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import com.edgarAndrew.PersonalFinanceTracker.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public BankAccount addBankAccount(String accountNumber, double initialBalance) {

        if(initialBalance < 0.0){
            throw new InsufficientFundsException("Initial Balance cannot be negative");
        }
        if (bankAccountRepository.existsByAccountNumber(accountNumber)) {
            throw new DuplicateAccountNumberException("Account number already in use: " + accountNumber);
        }

        // User Id from jwt token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        BankAccount bankAccount = new BankAccount(user, accountNumber, initialBalance);
        return bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public BankAccount getBankAccountById(Long id){
        return bankAccountRepository.findById(id).orElseThrow(()-> new BankNotFoundException("Bank not found with id : "+id));
    }

    @Transactional
    public void updateBankAccountBalance(BankAccount bankAccount, double amount, String type) {
        double currentBalance = bankAccount.getBalance();

        if (type.equals("INCOME")) {
            bankAccount.setBalance(currentBalance + amount);
        } else if (type.equals("EXPENSE")) {

            if (currentBalance >= amount) {
                bankAccount.setBalance(currentBalance - amount);
            } else {
                throw new InsufficientFundsException("Insufficient funds for the expense transaction");
            }
        }
    }

    @Transactional
    public void deleteBankAccount(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        BankAccount bank =  this.getBankAccountById(id);
        if(bank.getUser().getId().equals(user.getId())){
            bankAccountRepository.deleteById(id);
        }else{
            throw new AuthorizationException("Bank account with id " + id +" does not belong to you");
        }
    }

    public List<GetBankAccountsResponse> getMyBankAccounts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<BankAccount> bankAccounts = bankAccountRepository.findByUser(user);

        return bankAccounts.stream()
                .map(account -> new GetBankAccountsResponse(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getBalance()
                ))
                .collect(Collectors.toList());

    }
}
