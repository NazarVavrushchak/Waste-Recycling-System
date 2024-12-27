package com.ecowaste.recycling.dto.habit;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitDtoResponse {
    private Long id;
    private String title;
    private String description;
    private Integer difficulty;
    private Set<String> tags;
    private String imageUrl;
    private LocalDateTime createdAt;
    private boolean completed;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int durationInDays;
}