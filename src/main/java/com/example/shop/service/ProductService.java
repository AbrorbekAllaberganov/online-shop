package com.example.shop.service;

import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.PaginationGetDto;
import com.example.shop.dto.ProductDto;
import com.example.shop.dto.ProductGetDto;
import com.example.shop.entity.Attachment;
import com.example.shop.entity.Catalog;
import com.example.shop.entity.Product;
import com.example.shop.repository.AttachmentRepository;
import com.example.shop.repository.CatalogRepository;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;
    private final AttachmentRepository attachmentRepository;

    public ApiResponse saveProduct(ProductDto productDto) {
        Optional<Catalog> catalogOptional = catalogRepository.findById(productDto.getCatalogId());
        if (catalogOptional.isEmpty())
            return new ApiResponse("catalog not found", false);

        List<Attachment> attachmentList = new ArrayList<>();
        for (String hashId : productDto.getPhotoListHashId()) {
            Optional<Attachment> attachmentOptional = attachmentRepository.findByHashId(hashId);
            if (attachmentOptional.isEmpty())
                return new ApiResponse("file not found - " + hashId, false);
            attachmentList.add(attachmentOptional.get());
        }

        Product product = new Product();
        mapDtoToEntity(productDto, product, catalogOptional.get(), attachmentList);
        product.setIsActive(true);
        productRepository.save(product);

        return new ApiResponse("Product saved", true, new ProductGetDto(product));
    }

    public ApiResponse updateProduct(Long id, ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            return new ApiResponse("Product not found", false);

        Optional<Catalog> catalogOptional = catalogRepository.findById(productDto.getCatalogId());
        if (catalogOptional.isEmpty())
            return new ApiResponse("Catalog not found", false);

        List<Attachment> attachmentList = new ArrayList<>();
        for (String hashId : productDto.getPhotoListHashId()) {
            Optional<Attachment> attachmentOptional = attachmentRepository.findByHashId(hashId);
            if (attachmentOptional.isEmpty())
                return new ApiResponse("file not found - " + hashId, false);
            attachmentList.add(attachmentOptional.get());
        }

        Product product = productOptional.get();
        mapDtoToEntity(productDto, product, catalogOptional.get(), attachmentList);
        productRepository.save(product);

        return new ApiResponse("Product updated", true, new ProductGetDto(product));
    }

    public ApiResponse getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional
                .map(product -> new ApiResponse("Product found", true, new ProductGetDto(product)))
                .orElseGet(() -> new ApiResponse("Product not found", false));

    }

    public ApiResponse deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            return new ApiResponse("Product not found", false);

        Product product = productOptional.get();
        product.setIsActive(false);
        productRepository.save(product);

        return new ApiResponse("Product deleted (deactivated)", true);
    }

    public ApiResponse getProductsByCatalogId(Long catalogId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> productPage = productRepository.findAllByCatalogIdAndIsActiveTrue(catalogId, pageable);
        List<ProductGetDto> dtoList = productPage.getContent().stream()
                .map(ProductGetDto::new)
                .toList();

        return new ApiResponse("Products by catalog", true, new PaginationGetDto<>(
                dtoList,
                productPage.getTotalElements()
        ));
    }

    public ApiResponse getProductsByIsNew(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> productPage = productRepository.findAllByIsNewTrueAndIsActiveTrue(pageable);
        List<ProductGetDto> dtoList = productPage.getContent().stream()
                .map(ProductGetDto::new)
                .toList();

        return new ApiResponse("New products", true, new PaginationGetDto<>(
                dtoList,
                productPage.getTotalElements()
        ));
    }

    public ApiResponse getProductsByIsSelected(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> productPage = productRepository.findAllByIsSelectedTrueAndIsActiveTrue(pageable);
        List<ProductGetDto> dtoList = productPage.getContent().stream()
                .map(ProductGetDto::new)
                .toList();

        return new ApiResponse("Selected products", true, new PaginationGetDto<>(
                dtoList,
                productPage.getTotalElements()
        ));
    }

    private void mapDtoToEntity(ProductDto dto, Product product, Catalog catalog, List<Attachment> attachments) {
        product.setCatalog(catalog);
        product.setIsNew(dto.getIsNew());
        product.setIsSelected(dto.getIsSelected());
        product.setNameEn(dto.getNameEn());
        product.setNameRu(dto.getNameRu());
        product.setNameUz(dto.getNameUz());
        product.setDescriptionRu(dto.getDescriptionRu());
        product.setDescriptionEn(dto.getDescriptionEn());
        product.setDescriptionUz(dto.getDescriptionUz());
        product.setPrice(dto.getPrice());
        product.setPhotoList(attachments);
    }
}
