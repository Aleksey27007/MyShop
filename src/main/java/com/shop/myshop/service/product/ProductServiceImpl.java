package com.shop.myshop.service.product;

import com.shop.myshop.dto.ProductDto;
import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.mapper.ProductMapper;
import com.shop.myshop.model.Category;
import com.shop.myshop.model.Product;
import com.shop.myshop.repository.ImageRepository;
import com.shop.myshop.repository.ProductRepository;
import com.shop.myshop.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper modelMapper;
    private final ImageRepository imageRepository;
    private final CategoryService categoryService;

    @Override
    public Product addProduct(ProductDto productDto) {
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        if (category != null){
            return productRepository.save(createProduct(productDto, category));
        } else {
            Category newCategory = new Category(category.getName());
            categoryService.addCategory(newCategory);
            return productRepository.save(createProduct(productDto, category));
        }
    }

    private Product createProduct(ProductDto productDto, Category category) {
        return new Product(
                productDto.getName(),
                productDto.getBrand(),
                productDto.getPrice(),
                productDto.getQuantity(),
                productDto.getDescription(),
                category
        );
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {throw new ResourceNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProduct(ProductDto productDto, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,productDto))
                .map(productRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductDto productDto) {
        existingProduct.setName(productDto.getName());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setDescription(productDto.getDescription());

        Category category = categoryService.getCategoryByName(productDto.getName());
        existingProduct.setCategory(category);
        return  existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
