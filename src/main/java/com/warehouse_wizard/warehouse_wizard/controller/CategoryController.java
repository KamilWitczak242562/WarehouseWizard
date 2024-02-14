package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.dto.CategoryDto;
import com.warehouse_wizard.warehouse_wizard.mapper.CategoryMapper;
import com.warehouse_wizard.warehouse_wizard.model.Category;
import com.warehouse_wizard.warehouse_wizard.service.CategoryService;
import com.warehouse_wizard.warehouse_wizard.utils.RequiresLoggedInUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(CategoryMapper.mapToDtoList(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(CategoryMapper.categoryToCategoryDto(category));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @RequiresLoggedInUser
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        Category createdCategory = categoryService.createCategory(CategoryMapper.categoryDtoToCategory(categoryDto));
        return new ResponseEntity<>(CategoryMapper.categoryToCategoryDto(createdCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RequiresLoggedInUser
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable int id,
            @RequestBody CategoryDto updatedCategory) {
        Category category = categoryService.updateCategory(id, CategoryMapper.categoryDtoToCategory(updatedCategory));
        return ResponseEntity.ok(CategoryMapper.categoryToCategoryDto(category));
    }

    @DeleteMapping("/{id}")
    @RequiresLoggedInUser
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
