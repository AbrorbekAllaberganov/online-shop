package com.example.shop.repository;

import com.example.shop.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    boolean existsByNameUz(String nameUz);
    boolean existsByNameRu(String nameRu);
    boolean existsByNameEn(String nameEn);
    List<Catalog> findAllByCategoryIdOrderByCreatedAtDesc(Long categoryId);

    @Query(nativeQuery = true, value = """
            select c.* from catalog c
            where (:categoryId is null or c.category_id = :categoryId) and 
            (c.name_uz like :name or c.name_ru like :name or c.name_en like :name)
            """)
    List<Catalog> getCatalogsByNameAndCategory(@Param("categoryId") Long categoryId,
                                               @Param("name") String name);
}

