package com.example.shop.controller;

import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.CompanyDto;
import com.example.shop.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<ApiResponse> getCompany() {
        ApiResponse response = companyService.getCompany();
        return ResponseEntity.status(response.isStatus()?200:404).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        ApiResponse response = companyService.updateCompany(id, companyDto);
        return ResponseEntity.status(response.isStatus()?200:404).body(response);
    }

    @PostMapping("/init")
    public ResponseEntity<ApiResponse> initCompany(@RequestBody CompanyDto companyDto) {
        ApiResponse response = companyService.initCompany(companyDto);
        return ResponseEntity.status(response.isStatus()?200:400).body(response);
    }
}
