package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.model.Transaction;
import com.warehouse_wizard.warehouse_wizard.service.TransactionService;
import com.warehouse_wizard.warehouse_wizard.utils.RequiresLoggedInUser;
import com.warehouse_wizard.warehouse_wizard.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    @RequiresLoggedInUser
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Transaction>> getTransactionsByProduct(@PathVariable int id) {
        List<Transaction> transactions = transactionService.getTransactionsByProduct(id);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/type/{transactionType}")
    public ResponseEntity<List<Transaction>> getTransactionsByTransactionType(@PathVariable TransactionType transactionType) {
        List<Transaction> transactions = transactionService.getTransactionsByTransactionType(transactionType);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
