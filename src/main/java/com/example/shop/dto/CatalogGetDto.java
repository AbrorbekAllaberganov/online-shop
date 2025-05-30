package com.example.shop.dto;

import com.example.shop.entity.Attachment;
import com.example.shop.entity.Catalog;
import com.example.shop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CatalogGetDto {
    Long id;
    String name;
    Attachment photo;
    Boolean isActive;
    LocalDateTime createdAt;
    LocalDateTime updateAt;

    public CatalogGetDto(Catalog catalog, String lang){
        if (lang.equals("UZ"))
            this.name = catalog.getNameUz();
        else if (lang.equals("RU"))
            this.name = catalog.getNameRu();
        else
            this.name = catalog.getNameEn();

        this.id = catalog.getId();
        this.photo = catalog.getPhoto();
        this.isActive = catalog.getIsActive();
        this.createdAt = catalog.getCreatedAt();
        this.updateAt = catalog.getUpdateAt();
    }
}
