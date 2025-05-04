package com.example.shop.repository;

import com.example.shop.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    boolean existsByNameUz(String nameUz);
    boolean existsByNameRu(String nameRu);
    boolean existsByNameEn(String nameEn);
    List<Catalog> findAllByCategoryIdOrderByCreatedAtDesc(Long categoryId);
}

