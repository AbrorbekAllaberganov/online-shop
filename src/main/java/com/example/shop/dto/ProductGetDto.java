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
public class ProductGetDto {
    Long catalogId;
    String catalogNameUz;
    String catalogNameRu;
    String catalogNameEn;

    String nameUz;
    String nameRu;
    String nameEn;
    String descriptionUz;
    String descriptionRu;
    String descriptionEn;
    BigDecimal price;
    List<String> photoListHashId;
    Boolean isSelected;
    Boolean isNew;
    Boolean isActive;


    public ProductGetDto(Product product){
        this.catalogId = product.getCatalog().getId();
        this.catalogNameUz = product.getCatalog().getNameUz();
        this.catalogNameRu = product.getCatalog().getNameRu();
        this.catalogNameEn = product.getCatalog().getNameEn();
        this.descriptionEn = product.getDescriptionEn();
        this.descriptionRu = product.getDescriptionRu();
        this.descriptionUz = product.getDescriptionUz();
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
