package com.ecowaste.recycling.dto.dashboard.goal;

import com.ecowaste.recycling.enums.GoalStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoalRequestDto {
    private Long userId;

    @NotBlank(message = "Description is required")
    private String description;

    private String targetDate;

    private GoalStatus status;
}
