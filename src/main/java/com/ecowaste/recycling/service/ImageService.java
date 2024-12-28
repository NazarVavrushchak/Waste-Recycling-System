package com.ecowaste.recycling.service;

import com.ecowaste.recycling.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image saveImage(MultipartFile file, Object parentEntity);
    Image getImageById(Long id);
    void deleteImage(Long id);
    Object getParentEntityById(String parentEntityType, Long parentEntityId);
}