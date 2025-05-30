package com.example.shop.service;

import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.CatalogDto;
import com.example.shop.dto.CatalogGetDto;
import com.example.shop.entity.Attachment;
import com.example.shop.entity.Catalog;
import com.example.shop.entity.Category;
import com.example.shop.exxeption.BadRequestException;
import com.example.shop.repository.AttachmentRepository;
import com.example.shop.repository.CatalogRepository;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final ProductRepository productRepository;

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
        catalog.setIsActive(true);

        catalogRepository.save(catalog);
        return new ApiResponse("Catalog created", true, catalog);
    }

    public ApiResponse getAllCatalogs(Boolean isActive, int page, int size, boolean asc) {
        Sort sort = asc ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return new ApiResponse("Catalog list", true, catalogRepository.findAllByIsActive(isActive, pageable));
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
        Optional<Catalog> catalogOptional = catalogRepository.findById(id);

        if (catalogOptional.isEmpty()) {
            return new ApiResponse("Catalog not found", false);
        }

        if (productRepository.existsByCatalog_IdAndIsActiveTrue(id))
            throw new BadRequestException("product exists for this catalog");

        Catalog catalog = catalogOptional.get();
        catalog.setIsActive(false);

        catalogRepository.save(catalog);

        return new ApiResponse("Catalog deleted", true);
    }


    public ApiResponse getCatalogsByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            return new ApiResponse("Category not found", false);
        }

        List<Catalog> catalogs = catalogRepository.findAllByCategoryIdOrderByCreatedAtDesc(categoryId);
        return new ApiResponse("Catalogs by category", true, catalogs);
    }

    public ApiResponse getCatalogsByName(Boolean isActive, Long categoryId, String name) {
        if (categoryId != null)
            if (!categoryRepository.existsById(categoryId))
                return new ApiResponse("category not found", false);

        name = "%" + name + "%";

        return new ApiResponse("catalogs", true,
                catalogRepository.getCatalogsByNameAndCategory(categoryId, name, isActive)
        );
    }

    public ApiResponse changeStatus(Long catalogId, Boolean status) {
        Optional<Catalog> catalogOpt = catalogRepository.findById(catalogId);
        if (catalogOpt.isEmpty()) {
            return new ApiResponse("Catalog not found", false);
        }

        Catalog catalog = catalogOpt.get();
        catalog.setIsActive(status);

        catalogRepository.save(catalog);

        return new ApiResponse("catalog updated", true, catalog);
    }

    public ApiResponse getAllByLang(Long categoryId, String lang) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            return new ApiResponse("Category not found", false);
        }

        List<CatalogGetDto> catalogGetDtoList =
                catalogRepository.findAllByCategoryIdAndIsActiveOrderByCreatedAtDesc(
                        categoryId, true
                ).
                        stream()
                        .map(catalog -> new CatalogGetDto(catalog, lang))
                        .toList();

        return new ApiResponse("catalogs", true, catalogGetDtoList);
    }
}
