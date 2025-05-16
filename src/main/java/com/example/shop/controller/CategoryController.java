package com.example.shop.controller;

import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.CategoryDto;
import com.example.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryDto categoryDto) {
        ApiResponse response = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(response.isStatus()?200:409).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(
            @RequestParam(defaultValue = "true") Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponse response = categoryService.getAllCategories(isActive, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/without-pagination")
    public ResponseEntity<ApiResponse> getAllCategoriesWithoutPagination(
            @RequestParam(defaultValue = "true") Boolean isActive,
            @RequestParam(defaultValue = "") String name) {
        ApiResponse response = categoryService.getAllCategoriesWithoutPagination(isActive, name);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        ApiResponse response = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.status(response.isStatus()?200:404).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        ApiResponse response = categoryService.deleteCategory(id);
        return ResponseEntity.status(response.isStatus()?200:404).body(response);
    }


    @PutMapping("/change-status")
    public ResponseEntity<ApiResponse> changeStatus(
            @RequestParam Long categoryId,
            @RequestParam Boolean status
    ){
        ApiResponse response = categoryService.changeStatus(categoryId, status);
        return ResponseEntity.status(response.isStatus()?200:404).body(response);
    }

}
