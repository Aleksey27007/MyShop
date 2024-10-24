package com.shop.myshop.service.bucket;

import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.model.Bucket;
import com.shop.myshop.model.User;
import com.shop.myshop.repository.BucketProductRepository;
import com.shop.myshop.repository.BucketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService{
    private final BucketRepository bucketRepository;
    private final BucketProductRepository bucketProductRepository;
    private final AtomicLong bucketIdGenerator = new AtomicLong(0);
    @Override
    public Bucket getBucket(Long id) {
        Bucket bucket = bucketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bucket not found"));
        BigDecimal totalAmount = bucket.getTotalAmount();
        bucket.setTotalAmount(totalAmount);
        return bucketRepository.save(bucket);
    }

    @Transactional
    @Override
    public void clearBucket(Long id) {
        Bucket bucket = getBucket(id);
        bucketProductRepository.deleteAllByBucketId(id);
        bucket.getProducts().clear();
        bucketRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Bucket bucket = getBucket(id);
        return bucket.getTotalAmount();
    }

    @Override
    public Long initializeNewBucket(User user) {
        Bucket newBucket = new Bucket();
        Long newCartId = bucketIdGenerator.incrementAndGet();
        newBucket.setId(newCartId);
        newBucket.setUser(user);
        return bucketRepository.save(newBucket).getId();
    }

    @Override
    public Bucket getBucketByUserId(Long userId) {
        return bucketRepository.findByUserId(userId);
    }
}
