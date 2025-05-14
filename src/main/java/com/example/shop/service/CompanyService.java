package com.example.shop.service;

import com.example.shop.dto.ApiResponse;
import com.example.shop.dto.CompanyDto;
import com.example.shop.entity.Company;
import com.example.shop.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Cacheable("companyDto")
    public ApiResponse getCompany() {
        Optional<Company> companyOptional = companyRepository.findTopByOrderByIdAsc();
        return companyOptional
                .map(company -> new ApiResponse("company info", true, company))
                .orElseGet(() -> new ApiResponse("company info is not found", true, new ArrayList<>()));

    }

    @Transactional
    @CacheEvict(value = "companyDto", allEntries = true)
    public ApiResponse updateCompany(Long id, CompanyDto updatedData) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty())
            return new ApiResponse("company not found", false);

        Company company = companyOptional.get();

        company.setAboutCompanyUz(updatedData.getAboutCompanyUz());
        company.setAboutCompanyRu(updatedData.getAboutCompanyRu());
        company.setAboutCompanyEn(updatedData.getAboutCompanyEn());
        company.setPhoneNumber(updatedData.getPhoneNumber());
        company.setTelegramUrl(updatedData.getTelegramUrl());
        company.setLongitude(updatedData.getLongitude());
        company.setLatitude(updatedData.getLatitude());
        company.setAddressUz(updatedData.getAddressUz());
        company.setAddressRu(updatedData.getAddressRu());
        company.setAddressEn(updatedData.getAddressEn());
        company.setMainPhoneNumber(updatedData.getMainPhoneNumber());
        company.setEmail(updatedData.getEmail());

        companyRepository.save(company);

        return new ApiResponse("company", true, mapToDto(company));
    }

    public ApiResponse initCompany(CompanyDto companyDto) {
        if (companyRepository.existsBy()) {
            return new ApiResponse("Company data already exists. Initialization is allowed only once", false);
        }

        Company company = new Company();
        company.setAboutCompanyUz(companyDto.getAboutCompanyUz());
        company.setAboutCompanyRu(companyDto.getAboutCompanyRu());
        company.setAboutCompanyEn(companyDto.getAboutCompanyEn());
        company.setPhoneNumber(companyDto.getPhoneNumber());
        company.setTelegramUrl(companyDto.getTelegramUrl());
        company.setLongitude(companyDto.getLongitude());
        company.setLatitude(companyDto.getLatitude());
        company.setAddressUz(companyDto.getAddressUz());
        company.setAddressRu(companyDto.getAddressRu());
        company.setAddressEn(companyDto.getAddressEn());
        company.setMainPhoneNumber(companyDto.getMainPhoneNumber());
        company.setEmail(companyDto.getEmail());

        companyRepository.save(company);

        return new ApiResponse("company info created", true, mapToDto(company));
    }

    private CompanyDto mapToDto(Company company) {
        return new CompanyDto(
                company.getAboutCompanyUz(),
                company.getAboutCompanyRu(),
                company.getAboutCompanyEn(),
                company.getPhoneNumber(),
                company.getMainPhoneNumber(),
                company.getEmail(),
                company.getTelegramUrl(),
                company.getLongitude(),
                company.getLatitude(),
                company.getAddressUz(),
                company.getAddressRu(),
                company.getAddressEn()
        );
    }
}
