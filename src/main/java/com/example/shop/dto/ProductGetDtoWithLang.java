package com.example.shop.dto;

import com.example.shop.entity.Attachment;
import com.example.shop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductGetDtoWithLang {
    Long id;
    String name;
    String description;
    BigDecimal price;
    List<String> photoListHashId;
    Boolean isSelected;
    Boolean isNew;
    Boolean isActive;

    public ProductGetDtoWithLang(Product product, String lang) {
        this.id = product.getId();
        if (lang.equals("UZ"))
            this.description = product.getDescriptionUz();
        else if (lang.equals("RU"))
            this.description = product.getDescriptionRu();
        else
            this.description = product.getDescriptionEn();

        if (lang.equals("UZ"))
            this.name = product.getNameUz();
        else if (lang.equals("RU"))
            this.name = product.getNameRu();
        else
            this.name = product.getNameEn();

        this.price = product.getPrice();
        this.isSelected = product.getIsSelected();
        this.isNew = product.getIsNew();
        this.isActive = product.getIsActive();
        this.photoListHashId = product.getPhotoList()
                .stream()
                .map(Attachment::getHashId)
                .toList();
    }
}
