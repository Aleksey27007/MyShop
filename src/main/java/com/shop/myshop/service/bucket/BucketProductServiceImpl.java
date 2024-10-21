package com.shop.myshop.service.bucket;

import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.model.Bucket;
import com.shop.myshop.model.BucketProduct;
import com.shop.myshop.model.Product;
import com.shop.myshop.repository.BucketProductRepository;
import com.shop.myshop.repository.BucketRepository;
import com.shop.myshop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BucketProductServiceImpl implements BucketProductService {
    private final BucketProductRepository bucketProductRepository;
    private final BucketRepository bucketRepository;
    private final ProductService productService;
    private final BucketService bucketService;

    @Override
    public void addProductToBucket(Long bucketId, Long productId, int quantity) {
        Bucket bucket = bucketService.getBucket(bucketId);
        Product product = productService.getProductById(productId);
        BucketProduct bucketProduct = bucket.getProducts()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new BucketProduct());
        if (bucketProduct.getId() == null) {
            bucketProduct.setBucket(bucket);
            bucketProduct.setProduct(product);
            bucketProduct.setQuantity(quantity);
            bucketProduct.setUnitPrice(product.getPrice());
        } else {
            bucketProduct.setQuantity(bucketProduct.getQuantity() + quantity);
        }
        bucketProduct.setTotalPrice();
        bucket.addProduct(bucketProduct);
        bucketProductRepository.save(bucketProduct);
        bucketRepository.save(bucket);
    }

    @Override
    public void removeProductFromBucket(Long productId, Long bucketId) {
        Bucket bucket = bucketService.getBucket(bucketId);
        BucketProduct itemToRemove = getBucketProduct(bucketId, productId);
        bucket.removeProduct(itemToRemove);
        bucketRepository.save(bucket);
    }

    @Override
    public void updateProductQuantity(Long bucketId, Long productId, int quantity) {
        Bucket bucket = bucketService.getBucket(bucketId);
        bucket.getProducts()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = bucket.getProducts()
                .stream().map(BucketProduct::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        bucket.setTotalAmount(totalAmount);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketProduct getBucketProduct(Long bucketId, Long productId) {
        Bucket bucket = bucketService.getBucket(bucketId);
        return bucket.getProducts()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
