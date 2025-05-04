package com.example.shop.controller;

import com.example.shop.dto.ApiResponse;
import com.example.shop.entity.Attachment;
import com.example.shop.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/save")
    public ResponseEntity<?> saveFile(@RequestParam(name = "file") MultipartFile multipartFile) {
        ApiResponse response=attachmentService.saveAttachment(multipartFile);
        return ResponseEntity.status(response.isStatus()?201:409).body(response);
    }


    @GetMapping("/preview/{hashId}")
    public ResponseEntity<Resource> preview(@PathVariable String hashId) throws MalformedURLException {
        ApiResponse apiResponse = attachmentService.findByHashId(hashId);

        if (!apiResponse.isStatus()) {
            return ResponseEntity.notFound().build();
        }

        Attachment attachment = (Attachment) apiResponse.getData();

        Resource resource = new FileSystemResource(attachment.getLink());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String extension = attachment.getExtension().toLowerCase();
        MediaType contentType = switch (extension) {
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            case "pdf" -> MediaType.APPLICATION_PDF;
            default -> MediaType.IMAGE_JPEG;
        };
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + attachment.getName() + "\"")
                .contentType(contentType)
                .body(resource);
    }

}
