package com.shop.myshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
