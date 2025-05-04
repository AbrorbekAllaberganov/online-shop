package com.example.shop.repository;

import com.example.shop.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findTopByOrderByIdAsc();
    boolean existsBy();
}
