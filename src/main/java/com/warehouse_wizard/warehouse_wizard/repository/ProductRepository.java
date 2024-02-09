package com.warehouse_wizard.warehouse_wizard.repository;

import com.warehouse_wizard.warehouse_wizard.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
