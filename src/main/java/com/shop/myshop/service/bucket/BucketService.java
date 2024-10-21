package com.shop.myshop.service.bucket;

import com.shop.myshop.model.Bucket;

import java.math.BigDecimal;

public interface BucketService {
    Bucket getBucket(Long id);

    void clearBucket(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initializeNewBucket();

    Bucket getBucketByUserId(Long userId);
}
