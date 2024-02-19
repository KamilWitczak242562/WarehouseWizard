package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.dto.ProductDto;
import com.warehouse_wizard.warehouse_wizard.mapper.ProductMapper;
import com.warehouse_wizard.warehouse_wizard.model.Category;
import com.warehouse_wizard.warehouse_wizard.model.Product;
import com.warehouse_wizard.warehouse_wizard.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getProductsBelowMinimumTest() throws Exception {
        Category category1 = new Category();
        Product product1 = new Product(1, category1, "Test Product", "Test Description", 19.99, 7);
        Category category2 = new Category();
        Product product2 = new Product(1, category2, "Test Product", "Test Description", 19.99, 5);
        List<Product> products = List.of(product1, product2);
        when(productService.getBelowMinimum(anyInt())).thenReturn(products);

        mockMvc.perform(get("/api/product/belowMinimum?quantity=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(products.size())));
    }


    @Test
    public void getProductByIdTest() throws Exception {
        Category category = new Category();
        Product product = new Product(1, category, "Test Product", "Test Description", 19.99, 100);
        when(productService.getById(anyInt())).thenReturn(product);

        mockMvc.perform(get("/api/product/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProductTest() throws Exception {
        Category category = new Category();
        Product product = new Product(1, category, "Test Product", "Test Description", 19.99, 100);
        when(productService.updateProduct(eq(1), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/api/product/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ProductMapper.productToProductDto(product))))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllProductsTest() throws Exception {
        Category category1 = new Category();
        Product product1 = new Product(1, category1, "Test Product", "Test Description", 19.99, 100);
        Category category2 = new Category();
        Product product2 = new Product(1, category2, "Test Product", "Test Description", 19.99, 100);
        List<Product> products = List.of(product1, product2);
        when(productService.getAll()).thenReturn(products);

        mockMvc.perform(get("/api/product/allProducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(products.size())));
    }

    @Test
    public void addNewProductTest() throws Exception {
        Category category = new Category();
        Product product = new Product(1, category, "Test Product", "Test Description", 19.99, 100);
        when(productService.addNewProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/product/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ProductMapper.productToProductDto(product))))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteProductTest() throws Exception {
        doNothing().when(productService).deleteProduct(anyInt());

        mockMvc.perform(delete("/api/product/delete/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeQuantityTest() throws Exception {
        Category category = new Category();
        Product product = new Product(1, category, "Test Product", "Test Description", 19.99, 100);
        when(productService.removeQuantity(eq(1), anyInt())).thenReturn(product);

        mockMvc.perform(put("/api/product/{id}/removeQuantity", 1)
                        .param("quantityToRemove", "5"))
                .andExpect(status().isOk());
    }

    @Test
    public void changePriceTest() throws Exception {
        Category category = new Category();
        Product product = new Product(1, category, "Test Product", "Test Description", 19.99, 100);
        when(productService.changePrice(eq(1), anyDouble())).thenReturn(product);

        mockMvc.perform(put("/api/product/{id}/changePrice", 1)
                        .param("price", "25.00"))
                .andExpect(status().isOk());
    }

}