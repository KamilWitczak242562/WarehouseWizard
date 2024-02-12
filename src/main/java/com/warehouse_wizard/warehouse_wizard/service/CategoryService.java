package com.warehouse_wizard.warehouse_wizard.service;

import com.warehouse_wizard.warehouse_wizard.model.Category;
import com.warehouse_wizard.warehouse_wizard.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(int categoryId, Category updatedCategory) {
        Category existingCategory = getCategoryById(categoryId);
        if (updatedCategory.getName() == null) {
            existingCategory.setName(existingCategory.getName());
        } else {
            existingCategory.setName(updatedCategory.getName());
        }
        if (updatedCategory.getDescription() == null) {
            existingCategory.setDescription(existingCategory.getDescription());
        } else {
            existingCategory.setDescription(updatedCategory.getDescription());
        }

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(int categoryId) {
        Category category = getCategoryById(categoryId);
        categoryRepository.delete(category);
    }
}
