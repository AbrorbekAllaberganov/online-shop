package com.example.shop.repository;

import com.example.shop.entity.Company;
import com.example.shop.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    boolean existsBy();

    Optional<Gallery> findTopByOrderByIdAsc();
}
