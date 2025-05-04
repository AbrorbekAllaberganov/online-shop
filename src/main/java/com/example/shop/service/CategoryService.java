package com.example.shop.service;

import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.CategoryDto;
import com.example.shop.entity.Attachment;
import com.example.shop.entity.Category;
import com.example.shop.repository.AttachmentRepository;
import com.example.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, AttachmentRepository attachmentRepository) {
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
    }

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
        categoryRepository.save(category);



        return new ApiResponse("category", true, category);
    }

    public ApiResponse getAllCategories(int page, int size) {
        Sort sort = Sort.by("createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        return new ApiResponse("categories", true, categoryRepository.findAll(pageable));
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
        if (!categoryRepository.existsById(id)) {
            return new ApiResponse("category not found", false);
        }

        categoryRepository.deleteById(id);
        return new ApiResponse("category deleted", false);
    }
}
