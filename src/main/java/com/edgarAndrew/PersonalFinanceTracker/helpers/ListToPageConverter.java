package com.edgarAndrew.PersonalFinanceTracker.helpers;

import com.edgarAndrew.PersonalFinanceTracker.DTO.transaction.GetTransactionResponse;
import com.edgarAndrew.PersonalFinanceTracker.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ListToPageConverter {
    public static <T> Page<GetTransactionResponse> convertListToPage(List<Transaction> list, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<GetTransactionResponse> paginatedList;

        if (list.size() < startItem) {
            paginatedList = List.of(); // Empty list
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            paginatedList = list.subList(startItem, toIndex).stream().map(
                    transaction -> new GetTransactionResponse(
                            transaction.getId(),
                            transaction.getAmount(),
                            transaction.getType(),
                            transaction.getCategory(),
                            transaction.getDate(),
                            transaction.getBankAccount().getAccountNumber()
            )).toList();
        }

        return new PageImpl<>(paginatedList, pageable, list.size());
    }
}
