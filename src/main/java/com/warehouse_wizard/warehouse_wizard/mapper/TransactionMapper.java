package com.warehouse_wizard.warehouse_wizard.mapper;

import com.warehouse_wizard.warehouse_wizard.dto.TransactionDto;
import com.warehouse_wizard.warehouse_wizard.model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {

    public static Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(transactionDto.getTransaction_id());
        transaction.setQuantity(transactionDto.getQuantity());
        transaction.setProduct(ProductMapper.productDtoToProduct(transactionDto.getProduct()));
        transaction.setTransactionDate(transactionDto.getTransactionDate());
        transaction.setTransactionType(transactionDto.getTransactionType());
        return transaction;
    }

    public static TransactionDto transactionToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransaction_id(transaction.getTransaction_id());
        transactionDto.setQuantity(transaction.getQuantity());
        transactionDto.setProduct(ProductMapper.productToProductDto(transaction.getProduct()));
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        transactionDto.setTransactionType(transaction.getTransactionType());
        return transactionDto;
    }

    public static List<TransactionDto> mapToDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionMapper::transactionToTransactionDto)
                .collect(Collectors.toList());
    }

    public static List<Transaction> mapToList(List<TransactionDto> transactionsDto) {
        return transactionsDto.stream()
                .map(TransactionMapper::transactionDtoToTransaction)
                .collect(Collectors.toList());
    }
}
