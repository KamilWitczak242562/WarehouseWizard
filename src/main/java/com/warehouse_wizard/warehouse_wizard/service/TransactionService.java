package com.warehouse_wizard.warehouse_wizard.service;

import com.warehouse_wizard.warehouse_wizard.model.Product;
import com.warehouse_wizard.warehouse_wizard.model.Transaction;
import com.warehouse_wizard.warehouse_wizard.repository.TransactionRepository;
import com.warehouse_wizard.warehouse_wizard.utils.LoggedUser;
import com.warehouse_wizard.warehouse_wizard.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final LoggedUser loggedUser;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(int transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }

    public Transaction saveTransaction(Transaction transaction) {
        logger.info("New transaction created for product with id " + transaction.getProduct().getProduct_id() +
                ", type: " + transaction.getTransactionType() + " by user " + loggedUser.getLoggedUser().getEmail());
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(int transactionId) {
        logger.info("Transaction deleted with id " + transactionId + " by user " + loggedUser.getLoggedUser().getEmail());
        transactionRepository.deleteById(transactionId);
    }

    public List<Transaction> getTransactionsByProduct(int id) {
        return transactionRepository.findByProduct(id);
    }

    public List<Transaction> getTransactionsByTransactionType(TransactionType transactionType) {
        return transactionRepository.findByTransactionType(transactionType);
    }

}
