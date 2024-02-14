package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.dto.ProductDto;
import com.warehouse_wizard.warehouse_wizard.mapper.CategoryMapper;
import com.warehouse_wizard.warehouse_wizard.mapper.ProductMapper;
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
    public ResponseEntity<List<ProductDto>> getProductsBelowMinimum(@RequestParam int quantity) {
        List<Product> products = productService.getBelowMinimum(quantity);
        return ResponseEntity.ok(ProductMapper.mapToDtoList(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int id) {
        Product product = productService.getById(id);
        if (product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(ProductMapper.productToProductDto(product));
        }
    }

    @PutMapping("/{id}")
    @RequiresLoggedInUser
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        Product product = productService.updateProduct(id, ProductMapper.productDtoToProduct(productDto));
        return ResponseEntity.ok(ProductMapper.productToProductDto(product));
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(ProductMapper.mapToDtoList(products));
    }

    @PostMapping("/addProduct")
    @RequiresLoggedInUser
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody ProductDto productDto) {
        Product product = productService.addNewProduct(ProductMapper.productDtoToProduct(productDto));
        return new ResponseEntity<>(ProductMapper.productToProductDto(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @RequiresLoggedInUser
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/addQuantity")
    @RequiresLoggedInUser
    public ResponseEntity<ProductDto> addQuantity(@PathVariable int id, @RequestParam int quantityToAdd) {
        Product product = productService.addQuantity(id, quantityToAdd);
        return ResponseEntity.ok(ProductMapper.productToProductDto(product));
    }

    @PutMapping("/{id}/removeQuantity")
    @RequiresLoggedInUser
    public ResponseEntity<ProductDto> removeQuantity(@PathVariable int id, @RequestParam int quantityToRemove) {
        Product product = productService.removeQuantity(id, quantityToRemove);
        return ResponseEntity.ok(ProductMapper.productToProductDto(product));
    }

    @PutMapping("/{id}/changePrice")
    @RequiresLoggedInUser
    public ResponseEntity<ProductDto> changePrice(@PathVariable int id, @RequestParam double price) {
        Product product = productService.changePrice(id, price);
        return ResponseEntity.ok(ProductMapper.productToProductDto(product));
    }

}

