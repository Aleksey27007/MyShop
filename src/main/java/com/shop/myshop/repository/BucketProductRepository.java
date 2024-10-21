package com.shop.myshop.repository;

import com.shop.myshop.model.BucketProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketProductRepository extends JpaRepository<BucketProduct, Long> {
    void deleteAllByBucketId(Long id);
}
