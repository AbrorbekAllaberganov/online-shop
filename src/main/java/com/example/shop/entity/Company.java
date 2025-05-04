package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 1023)
    String aboutCompanyUz;

    @Column(length = 1023)
    String aboutCompanyRu;

    @Column(length = 1023)
    String aboutCompanyEn;

    @ElementCollection
    List<String> phoneNumber;

    String mainPhoneNumber;

    String email;

    String telegramUrl;

    Double longitude;

    Double Latitude;

    String addressUz;

    String addressRu;

    String addressEn;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

}
