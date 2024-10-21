package com.shop.myshop.service.product;

import com.shop.myshop.dto.ProductDto;
import com.shop.myshop.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductDto productDto, Long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String category, String name);

    Long countProductsByBrandAndName(String brand, String name);
}
