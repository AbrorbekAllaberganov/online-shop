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

    // Create
    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryDto categoryDto) {
        ApiResponse response = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(response.isStatus()?200:409).body(response);
    }

    // Read (Paginated, Sorted by createdAt)
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponse response = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok(response);
    }


    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        ApiResponse response = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.status(response.isStatus()?200:404).body(response);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        ApiResponse response = categoryService.deleteCategory(id);
        return ResponseEntity.status(response.isStatus()?200:404).body(response);
    }
}
