package com.example.shop.service;

import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.CatalogDto;
import com.example.shop.entity.Attachment;
import com.example.shop.entity.Catalog;
import com.example.shop.entity.Category;
import com.example.shop.repository.AttachmentRepository;
import com.example.shop.repository.CatalogRepository;
import com.example.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository, CategoryRepository categoryRepository, AttachmentRepository attachmentRepository) {
        this.catalogRepository = catalogRepository;
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public ApiResponse createCatalog(CatalogDto catalogDto) {
        if (catalogRepository.existsByNameUz(catalogDto.getNameUz()) ||
                catalogRepository.existsByNameRu(catalogDto.getNameRu()) ||
                catalogRepository.existsByNameEn(catalogDto.getNameEn())) {
            return new ApiResponse("Catalog name already exists", false);
        }

        Optional<Category> categoryOpt = categoryRepository.findById(catalogDto.getCategoryId());
        if (categoryOpt.isEmpty()) {
            return new ApiResponse("Category not found", false);
        }

        Optional<Attachment> attachmentOptional = attachmentRepository.findByHashId(catalogDto.getHashIdPhoto());
        if (attachmentOptional.isEmpty()) {
            return new ApiResponse("file not found", false);
        }

        Attachment attachment = attachmentOptional.get();

        Catalog catalog = new Catalog();
        catalog.setNameUz(catalogDto.getNameUz());
        catalog.setNameRu(catalogDto.getNameRu());
        catalog.setNameEn(catalogDto.getNameEn());
        catalog.setCategory(categoryOpt.get());
        catalog.setPhoto(attachment);

        catalogRepository.save(catalog);
        return new ApiResponse("Catalog created", true, catalog);
    }

    public ApiResponse getAllCatalogs(int page, int size, boolean asc) {
        Sort sort = asc ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return new ApiResponse("Catalog list", true, catalogRepository.findAll(pageable));
    }

    public ApiResponse updateCatalog(Long id, CatalogDto catalogDto) {
        Optional<Catalog> catalogOpt = catalogRepository.findById(id);
        if (catalogOpt.isEmpty()) {
            return new ApiResponse("Catalog not found", false);
        }

        Optional<Category> categoryOpt = categoryRepository.findById(catalogDto.getCategoryId());
        if (categoryOpt.isEmpty()) {
            return new ApiResponse("Category not found", false);
        }


        Optional<Attachment> attachmentOptional = attachmentRepository.findByHashId(catalogDto.getHashIdPhoto());
        if (attachmentOptional.isEmpty()) {
            return new ApiResponse("file not found", false);
        }

        Attachment attachment = attachmentOptional.get();

        Catalog catalog = catalogOpt.get();
        catalog.setNameUz(catalogDto.getNameUz());
        catalog.setNameRu(catalogDto.getNameRu());
        catalog.setNameEn(catalogDto.getNameEn());
        catalog.setCategory(categoryOpt.get());
        catalog.setPhoto(attachment);

        catalogRepository.save(catalog);
        return new ApiResponse("Catalog updated", true, catalog);
    }

    public ApiResponse deleteCatalog(Long id) {
        if (!catalogRepository.existsById(id)) {
            return new ApiResponse("Catalog not found", false);
        }

        catalogRepository.deleteById(id);
        return new ApiResponse("Catalog deleted", true);
    }
    public ApiResponse getCatalogsByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            return new ApiResponse("Category not found", false);
        }

        List<Catalog> catalogs = catalogRepository.findAllByCategoryIdOrderByCreatedAtDesc(categoryId);
        return new ApiResponse("Catalogs by category", true, catalogs);
    }

}
