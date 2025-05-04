package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    Long catalogId;
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
}
