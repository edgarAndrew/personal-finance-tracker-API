package com.edgarAndrew.PersonalFinanceTracker.exceptions;

import com.edgarAndrew.PersonalFinanceTracker.exceptions.bank.BankNotFoundException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.bank.DuplicateAccountNumberException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.bank.InsufficientFundsException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.budget.BudgetNotFoundException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.budget.DuplicateBudgetException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.goal.GoalCompletedException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.goal.GoalNotFoundException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction.InvalidCategoryException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction.InvalidDateCombinationException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.transaction.TransactionNotFoundException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.user.AuthorizationException;
import com.edgarAndrew.PersonalFinanceTracker.exceptions.user.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Global error handler
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // User not found handler
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Bank not found handler
    @ExceptionHandler(BankNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleBankNotFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Insufficient funds handler
    @ExceptionHandler(InsufficientFundsException.class)
    public final ResponseEntity<ErrorDetails> handleInsufficientFundsException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateAccountNumberException.class)
    public final ResponseEntity<ErrorDetails> handleDuplicateAccountNumberException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BudgetNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleBudgetNotFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateBudgetException.class)
    public final ResponseEntity<ErrorDetails> handleDuplicateBudgetFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleTransactionNotFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCategoryException.class)
    public final ResponseEntity<ErrorDetails> handleCategoryNotFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateCombinationException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidDateCombinationException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Illegal Argument exception handler
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorDetails> handleIllegalArgumentException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Authorization exception handler
    @ExceptionHandler(AuthorizationException.class)
    public final ResponseEntity<ErrorDetails> handleAuthorizationArgumentException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(GoalNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleGoalNotFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GoalCompletedException.class)
    public final ResponseEntity<ErrorDetails> handleGoalCompletedException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        String errorMessage = "Database constraint violation: " + ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errorMessage,request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
        //return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
