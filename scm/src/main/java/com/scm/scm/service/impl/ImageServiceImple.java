package com.scm.scm.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.scm.helper.AppConstants;
import com.scm.scm.service.ImageService;

@Service
public class ImageServiceImple implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    //function that uploads image to server
    //returns url
    @Override
    public String uploadImage(MultipartFile profilePicture, String filename) {

        if (profilePicture == null || profilePicture.isEmpty() || profilePicture.getSize() == 0) {
            return null;
        }
        
        try {
            
            byte[] data = new byte[profilePicture.getInputStream().available()];

            profilePicture.getInputStream().read(data);

            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", filename
            ));

            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        
        return cloudinary
                .url()
                .transformation(
                    new Transformation<>()
                        .width(AppConstants.CONTACT_IMAGE_WIDTH)
                        .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                        .crop(AppConstants.CONTACT_IMAGE_CROP))
                .generate(publicId);
    }

    @Override
    public void deleteImage(String url) {
        
    }
    

}
