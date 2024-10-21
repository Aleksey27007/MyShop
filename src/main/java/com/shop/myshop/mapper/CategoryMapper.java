package com.shop.myshop.mapper;

import com.shop.myshop.dto.CategoryDto;
import com.shop.myshop.model.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final ModelMapper modelMapper;

    public CategoryDto convertToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public List<CategoryDto> getConvertedCategories(List<Category> categories) {
        return categories.stream().map(this::convertToDto).toList();
    }
}
