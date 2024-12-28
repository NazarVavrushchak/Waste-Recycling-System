package com.ecowaste.recycling.service_impl;

import com.ecowaste.recycling.entity.Event;
import com.ecowaste.recycling.entity.Habit;
import com.ecowaste.recycling.entity.Image;
import com.ecowaste.recycling.repository.EventRepo;
import com.ecowaste.recycling.repository.HabitRepo;
import com.ecowaste.recycling.repository.ImageRepo;
import com.ecowaste.recycling.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepo imageRepo;
    private final HabitRepo habitRepo;
    private final EventRepo eventRepo;

    @Override
    @SneakyThrows
    public Image saveImage(MultipartFile file, Object parentEntity) {
        Image image = Image.builder()
                .name(file.getName())
                .originalFileName(file.getOriginalFilename())
                .size(file.getSize())
                .contentType(file.getContentType())
                .bytes(file.getBytes())
                .build();

        if (parentEntity instanceof Habit) {
            image.setHabit((Habit) parentEntity);
        } else if (parentEntity instanceof Event) {
            image.setEvent((Event) parentEntity);
        }

        return imageRepo.save(image);
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found with ID: " + id));
    }

    @Override
    public void deleteImage(Long id) {
        imageRepo.deleteById(id);
    }

    @Override
    public Object getParentEntityById(String parentEntityType, Long parentEntityId) {
        if ("Habit".equalsIgnoreCase(parentEntityType)) {
            return habitRepo.findById(parentEntityId)
                    .orElseThrow(() -> new IllegalArgumentException("Habit not found with ID: " + parentEntityId));
        } else if ("Event".equalsIgnoreCase(parentEntityType)) {
            return eventRepo.findById(parentEntityId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + parentEntityId));
        } else {
            throw new IllegalArgumentException("Unsupported parent entity type: " + parentEntityType);
        }
    }
}