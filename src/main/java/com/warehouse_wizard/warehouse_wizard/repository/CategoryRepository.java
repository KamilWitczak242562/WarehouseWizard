package com.warehouse_wizard.warehouse_wizard.repository;

import com.warehouse_wizard.warehouse_wizard.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
