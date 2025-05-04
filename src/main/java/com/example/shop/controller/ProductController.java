package com.example.shop.controller;

import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.ProductDto;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody ProductDto productDto) {
        ApiResponse response = productService.saveProduct(productDto);
        return ResponseEntity.status(response.isStatus() ? 201 : 400).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ApiResponse response = productService.updateProduct(id, productDto);
        return ResponseEntity.status(response.isStatus() ? 200 : 404).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ApiResponse response = productService.getProductById(id);
        return ResponseEntity.status(response.isStatus() ? 200 : 404).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        ApiResponse response = productService.deleteProduct(id);
        return ResponseEntity.status(response.isStatus() ? 200 : 404).body(response);
    }

    @GetMapping("/by-catalog/{catalogId}")
    public ResponseEntity<ApiResponse> getByCatalogId(
            @PathVariable Long catalogId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ApiResponse response = productService.getProductsByCatalogId(catalogId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/new")
    public ResponseEntity<ApiResponse> getByIsNew(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ApiResponse response = productService.getProductsByIsNew(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/selected")
    public ResponseEntity<ApiResponse> getByIsSelected(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ApiResponse response = productService.getProductsByIsSelected(page, size);
        return ResponseEntity.ok(response);
    }
}
