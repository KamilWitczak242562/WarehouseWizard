package com.warehouse_wizard.warehouse_wizard.service;

import com.mysql.cj.log.Log;
import com.warehouse_wizard.warehouse_wizard.model.Category;
import com.warehouse_wizard.warehouse_wizard.repository.CategoryRepository;
import com.warehouse_wizard.warehouse_wizard.utils.LoggedUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LoggedUser loggedUser;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public Category createCategory(Category category) {
        logger.info("New category created, name: " + category.getName() + " by user " + loggedUser.getLoggedUser().getEmail());
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
        logger.info("Category " + categoryId + "changed" + " by user " + loggedUser.getLoggedUser().getEmail());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
        logger.info("Category " + categoryId + " deleted" + " by user " + loggedUser.getLoggedUser().getEmail());
    }
}
