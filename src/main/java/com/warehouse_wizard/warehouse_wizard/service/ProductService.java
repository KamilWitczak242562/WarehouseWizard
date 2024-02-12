package com.warehouse_wizard.warehouse_wizard.service;

import com.mysql.cj.exceptions.WrongArgumentException;
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

    public Product addNewProduct(int category_id, String name, String description, double price, int stock_quantity) {
        Product product = new Product();
        product.setCategory(categoryService.getCategoryById(category_id));
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock_quantity(stock_quantity);
        productRepository.save(product);
        return product;
    }

    /***
     * To implement
     */
    public Product updateProduct() {
        return new Product();
    }

    public Product deleteProduct(int id, String name) {
        Product product = getProductByIdOrName(id, name);
        if (product == null) {
            throw new WrongArgumentException("No such product!");
        }
        productRepository.delete(product);
        return product;
    }

    public Product addQuantity(int id, String name, int quantityToAdd) {
        Product product = getProductByIdOrName(id, name);
        product.setStock_quantity(product.getStock_quantity() + quantityToAdd);
        productRepository.save(product);
        Transaction transaction = new Transaction();
        transaction.setQuantity(quantityToAdd);
        transaction.setProduct(product);
        transaction.setTransactionType(TransactionType.IN);
        transactionService.saveTransaction(transaction);
        return product;
    }

    public Product removeQuantity(int id, String name, int quantityToRemove) {
        Product product = getProductByIdOrName(id, name);
        product.setStock_quantity(product.getStock_quantity() - quantityToRemove);
        productRepository.save(product);
        Transaction transaction = new Transaction();
        transaction.setQuantity(quantityToRemove);
        transaction.setProduct(product);
        transaction.setTransactionType(TransactionType.OUT);
        transactionService.saveTransaction(transaction);
        return product;
    }

    public Product changePrice(int id, String name, double price) {
        Product product = getProductByIdOrName(id, name);
        product.setPrice(price);
        productRepository.save(product);
        return product;
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Product getProductByIdOrName(int id, String name) {
        if (name == null) {
            return productRepository.findById(id).orElse(null);
        } else {
            return productRepository.findByName(name);
        }
    }
}
