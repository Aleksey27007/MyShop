package com.shop.myshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BucketProductDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
