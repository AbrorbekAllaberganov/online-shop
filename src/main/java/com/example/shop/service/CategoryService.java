package com.example.shop.service;

import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.CategoryDto;
import com.example.shop.dto.CategoryGetDto;
import com.example.shop.entity.Attachment;
import com.example.shop.entity.Category;
import com.example.shop.exxeption.BadRequestException;
import com.example.shop.repository.AttachmentRepository;
import com.example.shop.repository.CatalogRepository;
import com.example.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final CatalogRepository catalogRepository;

    public ApiResponse createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByNameUz(categoryDto.getNameUz()) ||
                categoryRepository.existsByNameRu(categoryDto.getNameRu())) {
            return new ApiResponse("Category name already exists", false);
        }

        Optional<Attachment> attachmentOptional = attachmentRepository.findByHashId(categoryDto.getHashIdPhoto());
        if (attachmentOptional.isEmpty()) {
            return new ApiResponse("file not found", false);
        }

        Attachment attachment = attachmentOptional.get();

        Category category = new Category();
        category.setNameUz(categoryDto.getNameUz());
        category.setNameRu(categoryDto.getNameRu());
        category.setNameEn(categoryDto.getNameEn());
        category.setPhoto(attachment);
        category.setIsActive(true);
        categoryRepository.save(category);



        return new ApiResponse("category", true, category);
    }

    public ApiResponse getAllCategories(Boolean isActive, int page, int size) {
        Sort sort = Sort.by("createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        return new ApiResponse("categories", true, categoryRepository.findAllByIsActive(isActive, pageable));
    }


    public ApiResponse updateCategory(Long id, CategoryDto updatedCategory) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            return new ApiResponse("category not found", false);


        Optional<Attachment> attachmentOptional = attachmentRepository.findByHashId(updatedCategory.getHashIdPhoto());
        if (attachmentOptional.isEmpty()) {
            return new ApiResponse("file not found", false);
        }

        Attachment attachment = attachmentOptional.get();

        Category category = categoryOptional.get();

        category.setNameUz(updatedCategory.getNameUz());
        category.setNameRu(updatedCategory.getNameRu());
        category.setNameEn(updatedCategory.getNameEn());
        category.setPhoto(attachment);

        categoryRepository.save(category);

        return new ApiResponse("category updated", true, category);
    }

    public ApiResponse deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isEmpty()) {
            return new ApiResponse("category not found", false);
        }

        if (catalogRepository.existsByCategory_IdAndIsActiveTrue(id)) {
            throw new BadRequestException("catalog exists for this category");
        }

        Category category = categoryOptional.get();
        category.setIsActive(false);


        categoryRepository.save(category);
        return new ApiResponse("category deleted", true);
    }

    public ApiResponse getAllCategoriesWithoutPagination(Boolean isActive, String name) {
        name = "%" + name + "%";
        List<Category> categoryList = categoryRepository.findAllByName(name, isActive);
        return new ApiResponse("categories", true, categoryList);
    }

    public ApiResponse changeStatus(Long categoryId, Boolean status) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            return new ApiResponse("category not found", false);
        }

        Category category = categoryOptional.get();
        category.setIsActive(status);


        categoryRepository.save(category);
        return new ApiResponse("category updated", true);
    }

    public ApiResponse getCategoriesByLang(String lang) {
        List<CategoryGetDto> categoryGetDtoList =
                categoryRepository.findAllByIsActiveOrderById(true)
                        .stream()
                        .map(category->new CategoryGetDto(category, lang))
                        .toList();

        return new ApiResponse("categories", true, categoryGetDtoList);
    }
}
