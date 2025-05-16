package com.example.shop.controller;


import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.CatalogDto;
import com.example.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // Create
    @PostMapping
    public ResponseEntity<ApiResponse> createCatalog(@RequestBody CatalogDto catalogDto) {
        ApiResponse response = catalogService.createCatalog(catalogDto);
        return ResponseEntity.status(response.isStatus() ? 200 : 409).body(response);
    }

    // Read (Paginated, Sorted by createdAt)
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCatalogs(
            @RequestParam(defaultValue = "true") Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean asc
    ) {
        ApiResponse response = catalogService.getAllCatalogs(isActive, page, size, asc);
        return ResponseEntity.ok(response);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCatalog(@PathVariable Long id, @RequestBody CatalogDto catalogDto) {
        ApiResponse response = catalogService.updateCatalog(id, catalogDto);
        return ResponseEntity.status(response.isStatus() ? 200 : 404).body(response);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCatalog(@PathVariable Long id) {
        ApiResponse response = catalogService.deleteCatalog(id);
        return ResponseEntity.status(response.isStatus() ? 200 : 404).body(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getCatalogsByCategoryId(@PathVariable Long categoryId) {
        ApiResponse response = catalogService.getCatalogsByCategoryId(categoryId);
        return ResponseEntity.status(response.isStatus() ? 200 : 404).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getCatalogsWithoutPagination(
            @RequestParam(defaultValue = "true") Boolean isActive,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "") String name) {
        ApiResponse response = catalogService.getCatalogsByName(isActive, categoryId, name);
        return ResponseEntity.status(response.isStatus() ? 200 : 404).body(response);
    }

    @PutMapping("/change-status")
    public ResponseEntity<ApiResponse> changeStatus(
            @RequestParam Long catalogId,
            @RequestParam Boolean status
    ){
        ApiResponse response = catalogService.changeStatus(catalogId, status);
        return ResponseEntity.status(response.isStatus()?200:404).body(response);
    }

}


