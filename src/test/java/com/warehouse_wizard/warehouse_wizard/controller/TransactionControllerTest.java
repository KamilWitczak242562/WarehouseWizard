package com.warehouse_wizard.warehouse_wizard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse_wizard.warehouse_wizard.model.Category;
import com.warehouse_wizard.warehouse_wizard.model.Product;
import com.warehouse_wizard.warehouse_wizard.model.Transaction;
import com.warehouse_wizard.warehouse_wizard.service.TransactionService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

import com.warehouse_wizard.warehouse_wizard.utils.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.Mockito.doNothing;

import java.util.Date;
import java.util.List;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void getAllTransactions() throws Exception {
        Category category = new Category();
        Product product = new Product(1, category, "Test Product", "Test Description", 19.99, 100);
        Date transactionDate = new Date();
        List<Transaction> transactions = List.of(
                new Transaction(1, product, 5, TransactionType.OUT, transactionDate),
                new Transaction(2, product, 10, TransactionType.IN, transactionDate)
        );
        when(transactionService.getAllTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/api/transaction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactions.size())));
    }


    @Test
    void getTransactionById() throws Exception {
        int transactionId = 1;
        Category category = new Category();
        Product product = new Product(1, category, "Smartphone", "Latest model", 999.99, 50);
        Date transactionDate = new Date();
        Transaction transaction = new Transaction(transactionId, product, 1, TransactionType.IN, transactionDate);
        when(transactionService.getTransactionById(transactionId)).thenReturn(transaction);

        mockMvc.perform(get("/api/transaction/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType", is("IN")))
                .andExpect(jsonPath("$.quantity", is(1)))
                .andExpect(jsonPath("$.product.name", is("Smartphone")));
    }


    @Test
    void getTransactionsByProduct() throws Exception {
        int productId = 1;
        Category category = new Category();
        Product product = new Product(productId, category, "Book Title", "Description here", 29.99, 100);
        Date transactionDate = new Date();
        List<Transaction> transactions = List.of(
                new Transaction(1, product, 2, TransactionType.IN, transactionDate),
                new Transaction(2, product, 3, TransactionType.OUT, transactionDate)
        );
        when(transactionService.getTransactionsByProduct(productId)).thenReturn(transactions);

        mockMvc.perform(get("/api/transaction/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactions.size())));
    }

    @Test
    public void deleteTransactionTest() throws Exception {
        int transactionId = 1;

        doNothing().when(transactionService).deleteTransaction(transactionId);

        mockMvc.perform(delete("/api/transaction/{id}", transactionId))
                .andExpect(status().isNoContent());
    }


    @Test
    void getTransactionsByTransactionType() throws Exception {
        TransactionType transactionType = TransactionType.IN;
        Category category = new Category();
        Product product = new Product(1, category, "Game Console", "Latest generation console", 499.99, 30);
        Date transactionDate = new Date();
        List<Transaction> transactions = List.of(
                new Transaction(1, product, 1, TransactionType.IN, transactionDate),
                new Transaction(2, product, 2, TransactionType.IN, transactionDate)
        );
        when(transactionService.getTransactionsByTransactionType(transactionType)).thenReturn(transactions);

        mockMvc.perform(get("/api/transaction/type/{transactionType}", transactionType.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactions.size())));
    }

}