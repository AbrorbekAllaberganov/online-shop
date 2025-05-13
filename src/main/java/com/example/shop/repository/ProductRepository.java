package com.example.shop.repository;

import com.example.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCatalogIdAndIsActiveTrue(Long catalogId, Pageable pageable);

    Page<Product> findAllByIsNewTrueAndIsActiveTrue(Pageable pageable);

    Page<Product> findAllByIsSelectedTrueAndIsActiveTrue(Pageable pageable);
    Page<Product> findAllByCatalogIdAndIsActive(Long catalogId,Boolean isActive, Pageable pageable);

    Page<Product> findAllByIsActive(Boolean isActive, Pageable pageable);

    boolean existsByCatalog_IdAndIsActiveTrue(Long id);
}

