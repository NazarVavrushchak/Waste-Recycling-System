package com.ecowaste.recycling.controller;

import com.ecowaste.recycling.dto.recyclingPoint.RecyclingPointRequestDto;
import com.ecowaste.recycling.dto.recyclingPoint.RecyclingPointResponseDto;
import com.ecowaste.recycling.service.RecyclingPointService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recycling-point")
@AllArgsConstructor
public class RecyclingPointController {
    private final RecyclingPointService recyclingPointService;

    @PostMapping("/create")
    public ResponseEntity<RecyclingPointResponseDto> createRecyclingPoint(@RequestBody RecyclingPointRequestDto requestDto,
                                                                          @RequestParam Long userId) {
        RecyclingPointResponseDto createdPoint = recyclingPointService.createRecyclingPoint(requestDto, userId);
        return ResponseEntity.ok(createdPoint);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RecyclingPointResponseDto> updateRecyclingPoint(@PathVariable Long id,
                                                                          @RequestBody RecyclingPointRequestDto requestDto) {
        RecyclingPointResponseDto updatedPoint = recyclingPointService.updateRecyclingPoint(id, requestDto);
        return ResponseEntity.ok(updatedPoint);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecyclingPoint(@PathVariable Long id) {
        recyclingPointService.deleteRecyclingPoint(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecyclingPointResponseDto> getRecyclingPointById(@PathVariable Long id) {
        RecyclingPointResponseDto recyclingPoint = recyclingPointService.getRecyclingPointById(id);
        return ResponseEntity.ok(recyclingPoint);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RecyclingPointResponseDto>> getAllRecyclingPoints() {
        List<RecyclingPointResponseDto> recyclingPoints = recyclingPointService.getAllRecyclingPoints();
        return ResponseEntity.ok(recyclingPoints);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<RecyclingPointResponseDto>> filterRecyclingPointsByWasteType(@RequestParam String wasteType) {
        List<RecyclingPointResponseDto> filteredPoints = recyclingPointService.filterRecyclingPointsByWasteType(wasteType);
        return ResponseEntity.ok(filteredPoints);
    }
}