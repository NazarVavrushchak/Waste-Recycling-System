package com.ecowaste.recycling.service;

import com.ecowaste.recycling.dto.recyclingPoint.RecyclingPointRequestDto;
import com.ecowaste.recycling.dto.recyclingPoint.RecyclingPointResponseDto;

import java.util.List;

public interface RecyclingPointService {
    RecyclingPointResponseDto createRecyclingPoint(RecyclingPointRequestDto requestDto , Long userId);

    RecyclingPointResponseDto updateRecyclingPoint(Long id, RecyclingPointRequestDto requestDto);

    void deleteRecyclingPoint(Long id);

    RecyclingPointResponseDto getRecyclingPointById(Long id);

    List<RecyclingPointResponseDto> getAllRecyclingPoints();

    List<RecyclingPointResponseDto> filterRecyclingPointsByWasteType(String wasteType);
}