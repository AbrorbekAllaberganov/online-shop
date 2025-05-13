package com.example.shop.controller;

import com.example.shop.dto.ApiResponse;
import com.example.shop.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping("/init")
    public ResponseEntity<ApiResponse> initGallery(@RequestParam String hashId) {
        ApiResponse response = galleryService.initGallery(hashId);
        return new ResponseEntity<>(response, response.isStatus() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getGallery() {
        ApiResponse response = galleryService.getGallery();
        return new ResponseEntity<>(response, response.isStatus() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/image")
    public ResponseEntity<ApiResponse> deleteImage(@RequestParam String hashId) {
        ApiResponse response = galleryService.deleteImage(hashId);
        return new ResponseEntity<>(response, response.isStatus() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
