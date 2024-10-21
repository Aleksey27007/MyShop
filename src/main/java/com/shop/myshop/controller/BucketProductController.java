package com.shop.myshop.controller;

import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.response.ApiResponse;
import com.shop.myshop.service.bucket.BucketProductService;
import com.shop.myshop.service.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/buckets")
public class BucketProductController {
    private final BucketProductService bucketProductService;
    private final BucketService bucketService;


    @PostMapping("/add-product") // http://localhost:8080/api/v1/buckets/add-product
    public ResponseEntity<ApiResponse> addProductToBucket(@RequestParam(required = false) Long bucketId,
                                                          @RequestParam Long productId,
                                                          @RequestParam Integer quantity) {
        try {
            if (bucketId == null) {
                bucketId = bucketService.initializeNewBucket();
            }
            bucketProductService.addProductToBucket(bucketId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/remove/{bucketId}/{productId}") // http://localhost:8080/api/v1/buckets/remove/1/1
    public ResponseEntity<ApiResponse> removeProductFromBucket(@PathVariable Long bucketId, @PathVariable Long productId) {
        try {
            bucketProductService.removeProductFromBucket(bucketId, productId);
            return ResponseEntity.ok(new ApiResponse("Remove product Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{bucketId}/{productId}") // http://localhost:8080/api/v1/update/1/1
    public ResponseEntity<ApiResponse> updateProductQuantity(@PathVariable Long bucketId,
                                                             @PathVariable Long productId,
                                                             @RequestParam Integer quantity) {
        try {
            bucketProductService.updateProductQuantity(bucketId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update product Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
