package com.example.shop.service;

import com.example.shop.dto.ApiResponse;
import com.example.shop.entity.Attachment;
import com.example.shop.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public ApiResponse saveAttachment(MultipartFile multipartFile) {
        Attachment attachment = new Attachment();

        attachment.setContentType(multipartFile.getContentType());
        attachment.setFileSize(multipartFile.getSize());
        attachment.setName(multipartFile.getOriginalFilename());
        attachment.setExtension(getExtension(attachment.getName()).toLowerCase());
        attachment.setHashId(UUID.randomUUID().toString());


        LocalDate date = LocalDate.now();

        // change value downloadPath
        String downloadPath = "downloads";
        String localPath = downloadPath + String.format(
                "/%d/%d/%d/%s",
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                attachment.getExtension().toLowerCase());

        attachment.setUploadPath(localPath);


        File file = new File(localPath);

        file.mkdirs();

        attachment.setLink(file.getAbsolutePath() + "/" + String.format("%s.%s", attachment.getHashId(), attachment.getExtension()));


        try {
            attachmentRepository.save(attachment);
            File fileNew = new File(file.getAbsolutePath() + "/" + String.format("%s.%s", attachment.getHashId(), attachment.getExtension()));
            multipartFile.transferTo(fileNew);
            return new ApiResponse("file saved", true, attachment);

        } catch (IOException e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public ApiResponse findByHashId(String hashId) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findByHashId(hashId);
        return attachmentOptional.map(attachment -> new ApiResponse("attachment", true, attachment)).orElseGet(() -> new ApiResponse("attachment not found", false));

    }
}
