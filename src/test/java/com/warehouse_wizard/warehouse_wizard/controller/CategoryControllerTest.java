package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.dto.CategoryDto;
import com.warehouse_wizard.warehouse_wizard.model.Category;
import com.warehouse_wizard.warehouse_wizard.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllCategoriesTest() throws Exception {
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(categories.size())));
    }


    @Test
    public void getCategoryByIdTest() throws Exception {
        int categoryId = 1;
        Category category = new Category();
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        mockMvc.perform(get("/api/category/{id}", categoryId))
                .andExpect(status().isOk());
    }

    @Test
    public void createCategoryTest() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        Category category = new Category();

        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateCategoryTest() throws Exception {
        int categoryId = 1;
        CategoryDto updatedCategory = new CategoryDto();
        Category category = new Category();

        when(categoryService.updateCategory(eq(categoryId), any(Category.class))).thenReturn(category);

        mockMvc.perform(put("/api/category/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCategoryTest() throws Exception {
        int categoryId = 1;

        doNothing().when(categoryService).deleteCategory(categoryId);

        mockMvc.perform(delete("/api/category/{id}", categoryId))
                .andExpect(status().isNoContent());
    }

}