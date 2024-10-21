package com.shop.myshop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class BucketDto {
    private Long cartId;
    private Set<BucketProductDto> items;
    private BigDecimal totalAmount;
}
