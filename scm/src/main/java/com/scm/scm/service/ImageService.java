package com.scm.scm.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    
    String uploadImage(MultipartFile profilePicture, String filename);

    String getUrlFromPublicId(String publicId);

    void deleteImage(String url);
}
