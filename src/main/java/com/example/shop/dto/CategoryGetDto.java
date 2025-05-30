package com.example.shop.dto;

import com.example.shop.entity.Attachment;
import com.example.shop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryGetDto {
    Long id;
    String name;
    Attachment photo;
    Boolean isActive;
    LocalDateTime createdAt;
    LocalDateTime updateAt;

    public CategoryGetDto(Category category, String lang){
        if (lang.equals("UZ"))
            this.name = category.getNameUz();
        else if (lang.equals("RU"))
            this.name = category.getNameRu();
        else
            this.name = category.getNameEn();

        this.id = category.getId();
        this.photo = category.getPhoto();
        this.isActive = category.getIsActive();
        this.createdAt = category.getCreatedAt();
        this.updateAt = category.getUpdateAt();
    }
}
