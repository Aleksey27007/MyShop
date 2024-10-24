package com.shop.myshop.controller;

import com.shop.myshop.dto.BucketDto;
import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.mapper.BucketMapper;
import com.shop.myshop.model.Bucket;
import com.shop.myshop.response.ApiResponse;
import com.shop.myshop.service.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/buckets")
public class BucketController {
    private final BucketService bucketService;
    private final BucketMapper bucketMapper;

    @GetMapping("/{bucketId}") //  http://localhost:8080/api/v1/buckets/1
    public ResponseEntity<ApiResponse> getBucket(@PathVariable Long bucketId) {
        try {
            Bucket bucket = bucketService.getBucket(bucketId);
            BucketDto bucketDto = bucketMapper.convertToDto(bucket);
            return ResponseEntity.ok(new ApiResponse("Success", bucketDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/clear/{bucketId}") // http://localhost:8080/api/v1/buckets/clear/1
    public ResponseEntity<ApiResponse> clearBucket( @PathVariable Long bucketId) {
        try {
            bucketService.clearBucket(bucketId);
            return ResponseEntity.ok(new ApiResponse("Clear Bucket Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/total-price/{bucketId}") // http://localhost:8080/api/v1/buckets/total-price/1
    public ResponseEntity<ApiResponse> getTotalAmount( @PathVariable Long bucketId) {
        try {
            BigDecimal totalPrice = bucketService.getTotalPrice(bucketId);
            return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
