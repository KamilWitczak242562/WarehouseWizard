package com.warehouse_wizard.warehouse_wizard.mapper;

import com.warehouse_wizard.warehouse_wizard.dto.ProductDto;
import com.warehouse_wizard.warehouse_wizard.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product productDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProduct_id(productDto.getProduct_id());
        product.setDescription(productDto.getDescription());
        product.setStock_quantity(productDto.getStock_quantity());
        product.setCategory(CategoryMapper.categoryDtoToCategory(productDto.getCategory()));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return product;
    }

    public static ProductDto productToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProduct_id(product.getProduct_id());
        productDto.setDescription(product.getDescription());
        productDto.setStock_quantity(product.getStock_quantity());
        productDto.setCategory(CategoryMapper.categoryToCategoryDto(product.getCategory()));
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public static List<ProductDto> mapToDtoList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    public static List<Product> mapToList(List<ProductDto> productsDto) {
        return productsDto.stream()
                .map(ProductMapper::productDtoToProduct)
                .collect(Collectors.toList());
    }
}
