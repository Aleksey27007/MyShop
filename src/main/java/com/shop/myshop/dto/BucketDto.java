package com.shop.myshop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class BucketDto {
    private Long id;
    private BigDecimal totalAmount;
    private String firstName;
}
