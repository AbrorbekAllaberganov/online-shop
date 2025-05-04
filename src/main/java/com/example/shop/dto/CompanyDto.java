package com.example.shop.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDto {
    String aboutCompanyUz;
    String aboutCompanyRu;
    String aboutCompanyEn;
    List<String> phoneNumber;
    String mainPhoneNumber;
    String email;
    String telegramUrl;
    Double longitude;
    Double Latitude;
    String addressUz;
    String addressRu;
    String addressEn;
}
