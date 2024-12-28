package com.ecowaste.recycling.controller;

import com.ecowaste.recycling.entity.Image;
import com.ecowaste.recycling.repository.ImageRepo;
import com.ecowaste.recycling.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepo imageRepo;
    private final ImageService imageService;

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image image = imageRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found with ID :" + id));
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam(value = "photo", required = false) MultipartFile file,
            @RequestParam(value = "parentEntityType", required = false) String parentEntityType,
            @RequestParam(value = "parentEntityId", required = false) Long parentEntityId
    ) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded or file is empty.");
        }

        try {
            Object parentEntity = null;
            if (parentEntityType != null && parentEntityId != null) {
                parentEntity = imageService.getParentEntityById(parentEntityType, parentEntityId);
            }

            Image savedImage = imageService.saveImage(file, parentEntity);
            String imageUrl = "/images/" + savedImage.getId();
            return ResponseEntity.ok(Map.of("url", imageUrl));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
}