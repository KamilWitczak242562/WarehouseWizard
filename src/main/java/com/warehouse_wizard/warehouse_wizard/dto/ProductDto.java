package com.warehouse_wizard.warehouse_wizard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int product_id;
    private CategoryDto category;
    private String name;
    private String description;
    private double price;
    private int stock_quantity;
}
