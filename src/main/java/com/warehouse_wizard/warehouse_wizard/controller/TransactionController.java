package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.dto.TransactionDto;
import com.warehouse_wizard.warehouse_wizard.mapper.TransactionMapper;
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
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(TransactionMapper.mapToDtoList(transactions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable int id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(TransactionMapper.transactionToTransactionDto(transaction));
        }
    }

    @DeleteMapping("/{id}")
    @RequiresLoggedInUser
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByProduct(@PathVariable int id) {
        List<Transaction> transactions = transactionService.getTransactionsByProduct(id);
        return ResponseEntity.ok(TransactionMapper.mapToDtoList(transactions));
    }

    @GetMapping("/type/{transactionType}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByTransactionType(@PathVariable TransactionType transactionType) {
        List<Transaction> transactions = transactionService.getTransactionsByTransactionType(transactionType);
        return ResponseEntity.ok(TransactionMapper.mapToDtoList(transactions));
    }
}
