package com.shop.myshop.service.bucket;

import com.shop.myshop.model.Bucket;
import com.shop.myshop.model.User;

import java.math.BigDecimal;

public interface BucketService {
    Bucket getBucket(Long id);

    void clearBucket(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initializeNewBucket(User user);

    Bucket getBucketByUserId(Long userId);
}
