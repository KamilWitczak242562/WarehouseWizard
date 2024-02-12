package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.model.Product;
import com.warehouse_wizard.warehouse_wizard.service.ProductService;
import com.warehouse_wizard.warehouse_wizard.utils.RequiresLoggedInUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/***
 * Update response body to match web http responses
 */
@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/belowMinimum")
    public @ResponseBody List<Product> getProductsBelowMinimum(@RequestParam int quantity) {
        return productService.getBelowMinimum(quantity);
    }

    @GetMapping("/{id}")
    public @ResponseBody Product getProductById(@PathVariable int id) {
        return productService.getById(id);
    }

    @GetMapping
    public @ResponseBody Product getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    /***
     * To implement
     */
    @PutMapping("/{id}")
    @RequiresLoggedInUser
    public @ResponseBody Product updateProduct(@PathVariable int id, @RequestBody String mock) {
        return new Product();
    }

    @GetMapping("/allProducts")
    public @ResponseBody List<Product> getAllProducts() {
        return productService.getAll();
    }

    @PostMapping("/addProduct")
    @RequiresLoggedInUser
    public @ResponseBody Product addNewProduct(@RequestParam int category_id, @RequestBody Map<String, String> nameAndDesc, @RequestParam double price, @RequestParam int stockQuantity) {
        return productService.addNewProduct(
                category_id,
                nameAndDesc.get("name"),
                nameAndDesc.get("description"),
                price,
                stockQuantity
        );
    }

    @DeleteMapping("/delete/{id}")
    @RequiresLoggedInUser
    public @ResponseBody Product deleteProduct(@PathVariable int id, @RequestParam(required = false) String name) {
        return productService.deleteProduct(id, name);
    }

    @PutMapping("/{id}/addQuantity")
    @RequiresLoggedInUser
    public @ResponseBody Product addQuantity(@PathVariable int id, @RequestParam(required = false) String name, @RequestParam int quantityToAdd) {
        return productService.addQuantity(id, name, quantityToAdd);
    }

    @PutMapping("/{id}/removeQuantity")
    @RequiresLoggedInUser
    public @ResponseBody Product removeQuantity(@PathVariable int id, @RequestParam(required = false) String name, @RequestParam int quantityToRemove) {
        return productService.removeQuantity(id, name, quantityToRemove);
    }

    @PutMapping("/{id}/changePrice")
    @RequiresLoggedInUser
    public @ResponseBody Product changePrice(@PathVariable int id, @RequestParam(required = false) String name, @RequestParam double price) {
        return productService.changePrice(id, name, price);
    }

}

