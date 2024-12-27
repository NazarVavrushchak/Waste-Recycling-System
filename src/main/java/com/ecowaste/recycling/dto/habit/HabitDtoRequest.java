package com.ecowaste.recycling.dto.habit;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitDtoRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 70, message = "Title length must not exceed 70 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @JsonProperty("difficulty")
    private Integer difficulty;

    public void setDifficulty(Object difficulty) {
        if (difficulty instanceof String) {
            this.difficulty = mapDifficulty((String) difficulty);
        } else if (difficulty instanceof Integer) {
            this.difficulty = (Integer) difficulty;
        }
    }

    private Integer mapDifficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                return 1;
            case "medium":
                return 2;
            case "hard":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid difficulty value: " + difficulty);
        }
    }

    private List<String> tags;
    private MultipartFile image;
    private Long userId;
    private boolean completed;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
}
