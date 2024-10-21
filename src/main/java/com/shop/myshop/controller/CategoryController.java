package com.shop.myshop.controller;

import com.shop.myshop.dto.CategoryDto;
import com.shop.myshop.exceptions.AlreadyExistsException;
import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.mapper.CategoryMapper;
import com.shop.myshop.model.Category;
import com.shop.myshop.response.ApiResponse;
import com.shop.myshop.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper modelMapper;

    @GetMapping("") // http://localhost:8080/api/v1/categories
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            List<CategoryDto> categoryDtos = modelMapper.getConvertedCategories(categories);
            return  ResponseEntity.ok(new ApiResponse("Found!", categoryDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("") // http://localhost:8080/api/v1/categories
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            Category category = categoryService.addCategory(name);
            CategoryDto categoryDto = modelMapper.convertToDto(category);
            return  ResponseEntity.ok(new ApiResponse("Success", categoryDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-id/{id}") // http://localhost:8080/api/v1/categories/1
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try {
            Category category = categoryService.getCategoryById(id);
            CategoryDto categoryDto = modelMapper.convertToDto(category);
            return  ResponseEntity.ok(new ApiResponse("Found", categoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-name/{name}") // http://localhost:8080/api/v1/categories/TV
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category category = categoryService.getCategoryByName(name);
            CategoryDto categoryDto = modelMapper.convertToDto(category);
            return  ResponseEntity.ok(new ApiResponse("Found", categoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/delete/{id}") // http://localhost:8080/api/v1/categories/delete/3
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return  ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}") // http://localhost:8080/api/v1/categories/update/3
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            CategoryDto categoryDto = modelMapper.convertToDto(updatedCategory);
            return ResponseEntity.ok(new ApiResponse("Update success!", categoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
