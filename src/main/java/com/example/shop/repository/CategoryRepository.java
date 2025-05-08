package com.example.shop.repository;

import com.example.shop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameUz(String nameUz);
    boolean existsByNameRu(String nameRu);
    boolean existsByNameEn(String nameEn);

    @Query(nativeQuery = true, value = """
            select c.* from category c
            where c.name_uz like :name or c.name_ru like :name or c.name_en like :name
            """)
    List<Category> findAllByName(String name);
}
