package com.example.shop.repository;

import com.example.shop.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByHashId(String hashId);
    boolean existsByHashId (String hashId);
}
