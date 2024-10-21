package com.shop.myshop.repository;

import com.shop.myshop.model.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Bucket findByUserId(Long userId);
}
