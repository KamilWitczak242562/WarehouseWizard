package com.warehouse_wizard.warehouse_wizard.repository;

import com.warehouse_wizard.warehouse_wizard.model.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM product WHERE stock_quantity < :quantity", nativeQuery = true)
    List<Product> getBelowMinimum(@Param("quantity") int quantity);

    @Query(value = "SELECT * FROM product WHERE name = :name", nativeQuery = true)
    Product findByName(@Param("name") String name);
}
