package com.shop.myshop.service.bucket;

import com.shop.myshop.model.BucketProduct;

public interface BucketProductService {
    void addProductToBucket(Long bucketId, Long productId, int quantity);

    void removeProductFromBucket(Long productId, Long bucketId);

    void updateProductQuantity(Long bucketId, Long productId, int quantity);

    BucketProduct getBucketProduct(Long bucketId, Long productId);
}
