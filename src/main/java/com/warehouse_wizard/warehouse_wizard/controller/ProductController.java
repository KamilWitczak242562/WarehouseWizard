package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.model.Product;
import com.warehouse_wizard.warehouse_wizard.service.ProductService;
import com.warehouse_wizard.warehouse_wizard.utils.RequiresLoggedInUser;
import lombok.AllArgsConstructor;
import ognl.enhance.OrderedReturn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/belowMinimum")
    public ResponseEntity<List<Product>> getProductsBelowMinimum(@RequestParam int quantity) {
        List<Product> products = productService.getBelowMinimum(quantity);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getById(id);
        if (product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(product);
        }
    }

    @PutMapping("/{id}")
    @RequiresLoggedInUser
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestParam int category_id, @RequestBody Map<String, String> nameAndDesc, @RequestParam double price, @RequestParam int stockQuantity) {
        Product product = productService.updateProduct(id, category_id, nameAndDesc.get("name"), nameAndDesc.get("description"), price, stockQuantity);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/addProduct")
    @RequiresLoggedInUser
    public ResponseEntity<Product> addNewProduct(@RequestParam int category_id, @RequestBody Map<String, String> nameAndDesc, @RequestParam double price, @RequestParam int stockQuantity) {
        Product product = productService.addNewProduct(category_id,
                nameAndDesc.get("name"),
                nameAndDesc.get("description"),
                price,
                stockQuantity);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @RequiresLoggedInUser
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/addQuantity")
    @RequiresLoggedInUser
    public ResponseEntity<Product> addQuantity(@PathVariable int id, @RequestParam int quantityToAdd) {
        Product product = productService.addQuantity(id, quantityToAdd);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}/removeQuantity")
    @RequiresLoggedInUser
    public ResponseEntity<Product> removeQuantity(@PathVariable int id, @RequestParam int quantityToRemove) {
        Product product = productService.removeQuantity(id, quantityToRemove);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}/changePrice")
    @RequiresLoggedInUser
    public ResponseEntity<Product> changePrice(@PathVariable int id, @RequestParam double price) {
        Product product = productService.changePrice(id, price);
        return ResponseEntity.ok(product);
    }

}

