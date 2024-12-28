package com.ecowaste.recycling.dto.recyclingPoint;

import com.ecowaste.recycling.enums.WasteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecyclingPointResponseDto {
    private Long id;

    private String name;

    private String address;

    private Set<WasteType> wasteType;

    private double latitude;

    private double longitude;

    private Set<DayOfWeek> workingDays;

    private String openingTime;

    private String closingTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long userId;
}