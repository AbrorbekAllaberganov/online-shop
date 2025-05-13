package com.example.shop.service;

import com.example.shop.dto.ApiResponse;
import com.example.shop.entity.Attachment;
import com.example.shop.entity.Company;
import com.example.shop.entity.Gallery;
import com.example.shop.repository.AttachmentRepository;
import com.example.shop.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final GalleryRepository galleryRepository;
    private final AttachmentRepository attachmentRepository;

    public ApiResponse initGallery(String hashId){

        Gallery gallery = new Gallery();
        Optional<Gallery> optionalGallery = galleryRepository.findTopByOrderByIdAsc();
        if (optionalGallery.isPresent())
            gallery = optionalGallery.get();


        Optional<Attachment> optionalAttachment = attachmentRepository.findByHashId(hashId);
        if (optionalAttachment.isEmpty())
            return new ApiResponse("attachment not found", false);

        List<Attachment> attachmentList = gallery.getPhotoList();
        attachmentList.add(optionalAttachment.get());

        gallery.setPhotoList(attachmentList);

        galleryRepository.save(gallery);

        return new ApiResponse("gallery", true, gallery);
    }

    public ApiResponse getGallery(){
        Optional<Gallery> galleryOptional = galleryRepository.findTopByOrderByIdAsc();
        return galleryOptional
                .map(gallery -> new ApiResponse("gallery", true, gallery))
                .orElseGet(() -> new ApiResponse("gallery is not found", false));
    }

    public ApiResponse deleteImage(String hashId){
        Optional<Attachment> optionalAttachment = attachmentRepository.findByHashId(hashId);
        if (optionalAttachment.isEmpty())
            return new ApiResponse("attachment not found", false);

        Optional<Gallery> galleryOptional = galleryRepository.findTopByOrderByIdAsc();
        if (galleryOptional.isEmpty())
            return new ApiResponse("gallery not exists", false);

        Gallery gallery = galleryOptional.get();

        List<Attachment> attachmentList = gallery.getPhotoList();
        attachmentList.removeIf(attachment -> attachment.getHashId().equals(hashId));

        gallery.setPhotoList(attachmentList);

        galleryRepository.save(gallery);
        return new ApiResponse("gallery", true, gallery);
    }


}
