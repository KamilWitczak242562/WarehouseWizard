package com.warehouse_wizard.warehouse_wizard.service;

import com.mysql.cj.exceptions.WrongArgumentException;
import com.warehouse_wizard.warehouse_wizard.mapper.ProductMapper;
import com.warehouse_wizard.warehouse_wizard.model.Product;
import com.warehouse_wizard.warehouse_wizard.model.Transaction;
import com.warehouse_wizard.warehouse_wizard.repository.ProductRepository;
import com.warehouse_wizard.warehouse_wizard.utils.LoggedUser;
import com.warehouse_wizard.warehouse_wizard.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final TransactionService transactionService;
    private final LoggedUser loggedUser;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
        logger.info("New product added, name: " + product.getName() + ", id " + product.getProduct_id() + " by user " + loggedUser.getLoggedUser().getEmail());
        return product;
    }

    public Product updateProduct(int id, Product product) {
        productRepository.deleteById(id);
        productRepository.save(product);
        return product;
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
        logger.info("Product deleted with id " + id + " by user " + loggedUser.getLoggedUser().getEmail());
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
        logger.info("Product price changed to " + price + ", for id " + product.getProduct_id() + " by user " + loggedUser.getLoggedUser().getEmail());
        return product;
    }
}
