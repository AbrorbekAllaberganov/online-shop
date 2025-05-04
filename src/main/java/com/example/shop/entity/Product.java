package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Catalog catalog;

    String nameUz;

    String nameRu;

    String nameEn;

    Boolean isActive;

    @Column(length = 10_000)
    String descriptionUz;


    @Column(length = 10_000)
    String descriptionRu;


    @Column(length = 10_000)
    String descriptionEn;

    BigDecimal price;

    @OneToMany
    List<Attachment> photoList;

    Boolean isSelected;

    Boolean isNew;


}
