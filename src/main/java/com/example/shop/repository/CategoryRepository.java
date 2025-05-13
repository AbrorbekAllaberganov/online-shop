package com.example.shop.repository;

import com.example.shop.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameUz(String nameUz);
    boolean existsByNameRu(String nameRu);
    boolean existsByNameEn(String nameEn);

    @Query(nativeQuery = true, value = """
    SELECT c.* FROM category c
    WHERE (
        LOWER(c.name_uz) LIKE LOWER(:name)
        OR LOWER(c.name_ru) LIKE LOWER(:name)
        OR LOWER(c.name_en) LIKE LOWER(:name)
    ) AND c.is_active = :isActive
    """)
    List<Category> findAllByName(@Param("name") String name, @Param("isActive") Boolean isActive);


    Page<Category> findAllByIsActive(Boolean isActive, Pageable pageable);
}
