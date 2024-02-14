package com.warehouse_wizard.warehouse_wizard.mapper;

import com.warehouse_wizard.warehouse_wizard.dto.CategoryDto;
import com.warehouse_wizard.warehouse_wizard.dto.ProductDto;
import com.warehouse_wizard.warehouse_wizard.model.Category;
import com.warehouse_wizard.warehouse_wizard.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static Category categoryDtoToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategory_id(categoryDto.getCategory_id());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

    public static CategoryDto categoryToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategory_id(category.getCategory_id());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }

    public static List<CategoryDto> mapToDtoList(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    public static List<Category> mapToList(List<CategoryDto> categoriesDto) {
        return categoriesDto.stream()
                .map(CategoryMapper::categoryDtoToCategory)
                .collect(Collectors.toList());
    }
}
