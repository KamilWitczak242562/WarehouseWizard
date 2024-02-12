package com.warehouse_wizard.warehouse_wizard.repository;

import com.warehouse_wizard.warehouse_wizard.model.Product;
import com.warehouse_wizard.warehouse_wizard.model.Transaction;
import com.warehouse_wizard.warehouse_wizard.utils.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT * FROM transaction WHERE product_id = :product_id", nativeQuery = true)
    List<Transaction> findByProduct(@Param("product_id")int product_id);
    @Query(value = "SELECT * FROM transaction WHERE transaction_type = :transactionType", nativeQuery = true)
    List<Transaction> findByTransactionType(@Param("transactionType")TransactionType transactionType);
}
