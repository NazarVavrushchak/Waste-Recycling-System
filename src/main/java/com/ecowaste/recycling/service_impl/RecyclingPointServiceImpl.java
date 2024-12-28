package com.ecowaste.recycling.service_impl;

import com.ecowaste.recycling.dto.recyclingPoint.RecyclingPointRequestDto;
import com.ecowaste.recycling.dto.recyclingPoint.RecyclingPointResponseDto;
import com.ecowaste.recycling.entity.RecyclingPoint;
import com.ecowaste.recycling.entity.User;
import com.ecowaste.recycling.entity.WorkingHours;
import com.ecowaste.recycling.repository.RecyclingPointRepo;
import com.ecowaste.recycling.repository.UserRepo;
import com.ecowaste.recycling.service.RecyclingPointService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RecyclingPointServiceImpl implements RecyclingPointService {
    private final RecyclingPointRepo recyclingPointRepo;
    private final UserRepo userRepo;

    @Override
    public RecyclingPointResponseDto createRecyclingPoint(RecyclingPointRequestDto requestDto, Long userid) {
        User user = userRepo.findById(Math.toIntExact(userid))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + requestDto.getUserId()));

        RecyclingPoint recyclingPoint = mapToEntity(requestDto);

        recyclingPoint.setUser(user);

        RecyclingPoint savedRecyclingPoint = recyclingPointRepo.save(recyclingPoint);

        return mapToResponseDto(savedRecyclingPoint);
    }

    @Override
    public RecyclingPointResponseDto updateRecyclingPoint(Long id, RecyclingPointRequestDto requestDto) {
        RecyclingPoint recyclingPoint = recyclingPointRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recycling Point not found with ID: " + id));

        recyclingPoint.setName(requestDto.getName());
        recyclingPoint.setAddress(requestDto.getAddress());
        recyclingPoint.setWasteType(requestDto.getWasteType());
        recyclingPoint.setLatitude(requestDto.getLatitude());
        recyclingPoint.setLongitude(requestDto.getLongitude());

        recyclingPoint.setWorkingHours(
                WorkingHours.builder()
                        .openingTime(requestDto.getOpeningTime())
                        .closingTime(requestDto.getClosingTime())
                        .workingDays(requestDto.getWorkingDays())
                        .build()
        );

        RecyclingPoint updatedRecyclingPoint = recyclingPointRepo.save(recyclingPoint);

        return mapToResponseDto(updatedRecyclingPoint);
    }

    @Override
    public void deleteRecyclingPoint(Long id) {
        if (!recyclingPointRepo.existsById(id)) {
            throw new IllegalArgumentException("Recycling Point not found with ID: " + id);
        }
        recyclingPointRepo.deleteById(id);
    }

    @Override
    public RecyclingPointResponseDto getRecyclingPointById(Long id) {
        RecyclingPoint recyclingPoint = recyclingPointRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recycling Point not found with ID: " + id));

        return mapToResponseDto(recyclingPoint);
    }

    @Override
    public List<RecyclingPointResponseDto> getAllRecyclingPoints() {
        List<RecyclingPoint> recyclingPoints = recyclingPointRepo.findAll();

        return recyclingPoints.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<RecyclingPointResponseDto> filterRecyclingPointsByWasteType(String wasteType) {
        List<RecyclingPoint> recyclingPoints = recyclingPointRepo.findByWasteType(wasteType);

        return recyclingPoints.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    private RecyclingPoint mapToEntity(RecyclingPointRequestDto requestDto) {
        return RecyclingPoint.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .wasteType(requestDto.getWasteType())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .workingHours(
                        WorkingHours.builder()
                                .openingTime(requestDto.getOpeningTime())
                                .closingTime(requestDto.getClosingTime())
                                .workingDays(requestDto.getWorkingDays())
                                .build()
                )
                .build();
    }

    private RecyclingPointResponseDto mapToResponseDto(RecyclingPoint recyclingPoint) {
        return RecyclingPointResponseDto.builder()
                .id(recyclingPoint.getId())
                .name(recyclingPoint.getName())
                .address(recyclingPoint.getAddress())
                .wasteType(recyclingPoint.getWasteType())
                .latitude(recyclingPoint.getLatitude())
                .longitude(recyclingPoint.getLongitude())
                .workingDays(recyclingPoint.getWorkingHours().getWorkingDays())
                .openingTime(recyclingPoint.getWorkingHours().getOpeningTime().toString())
                .closingTime(recyclingPoint.getWorkingHours().getClosingTime().toString())
                .createdAt(recyclingPoint.getCreatedAt())
                .updatedAt(recyclingPoint.getUpdatedAt())
                .userId(recyclingPoint.getUser().getId())
                .build();
    }
}