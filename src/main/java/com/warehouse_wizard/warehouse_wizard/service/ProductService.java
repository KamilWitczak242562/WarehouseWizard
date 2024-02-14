package com.warehouse_wizard.warehouse_wizard.service;

import com.mysql.cj.exceptions.WrongArgumentException;
import com.warehouse_wizard.warehouse_wizard.mapper.ProductMapper;
import com.warehouse_wizard.warehouse_wizard.model.Product;
import com.warehouse_wizard.warehouse_wizard.model.Transaction;
import com.warehouse_wizard.warehouse_wizard.repository.ProductRepository;
import com.warehouse_wizard.warehouse_wizard.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final TransactionService transactionService;

    public List<Product> getBelowMinimum(int quantity) {
        return productRepository.getBelowMinimum(quantity);
    }

    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product addNewProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(int id, Product product) {
        productRepository.deleteById(id);
        productRepository.save(product);
        return product;
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public Product addQuantity(int id, int quantityToAdd) {
        Product product = productRepository.findById(id).orElse(null);
        product.setStock_quantity(product.getStock_quantity() + quantityToAdd);
        productRepository.save(product);
        Transaction transaction = new Transaction();
        transaction.setQuantity(quantityToAdd);
        transaction.setProduct(product);
        transaction.setTransactionType(TransactionType.IN);
        transactionService.saveTransaction(transaction);
        return product;
    }

    public Product removeQuantity(int id, int quantityToRemove) {
        Product product = productRepository.findById(id).orElse(null);
        product.setStock_quantity(product.getStock_quantity() - quantityToRemove);
        productRepository.save(product);
        Transaction transaction = new Transaction();
        transaction.setQuantity(quantityToRemove);
        transaction.setProduct(product);
        transaction.setTransactionType(TransactionType.OUT);
        transactionService.saveTransaction(transaction);
        return product;
    }

    public Product changePrice(int id, double price) {
        Product product = productRepository.findById(id).orElse(null);
        product.setPrice(price);
        productRepository.save(product);
        return product;
    }
}
