package com.example.shop.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CatalogDto {
    String nameUz;
    String nameRu;
    String nameEn;
    Long categoryId;
    String hashIdPhoto;
}
