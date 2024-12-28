package com.ecowaste.recycling.dto.dashboard.goal;

import com.ecowaste.recycling.enums.GoalStatus;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoalDtoResponse {
    private Long id;
    private String description;
    private GoalStatus status;
    private String targetDate;
}