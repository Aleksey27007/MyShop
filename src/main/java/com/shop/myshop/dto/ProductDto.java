package com.shop.myshop.dto;

import com.shop.myshop.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private Long categoryId;
    private List<ImageDto> images;
}
