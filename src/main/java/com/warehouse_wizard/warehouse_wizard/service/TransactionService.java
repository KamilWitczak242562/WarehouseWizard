package com.warehouse_wizard.warehouse_wizard.service;

import com.warehouse_wizard.warehouse_wizard.model.Product;
import com.warehouse_wizard.warehouse_wizard.model.Transaction;
import com.warehouse_wizard.warehouse_wizard.repository.TransactionRepository;
import com.warehouse_wizard.warehouse_wizard.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(int transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(int transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public List<Transaction> getTransactionsByProduct(int id) {
        return transactionRepository.findByProduct(id);
    }

    public List<Transaction> getTransactionsByTransactionType(TransactionType transactionType) {
        return transactionRepository.findByTransactionType(transactionType);
    }

}
