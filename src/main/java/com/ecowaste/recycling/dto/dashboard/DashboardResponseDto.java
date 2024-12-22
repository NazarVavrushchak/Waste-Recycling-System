package com.ecowaste.recycling.dto.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponseDto {
    private String userName;
    private long completedHabits;
    private long eventsParticipated;
    private String currentGoal;
    private long daysActiveLastMonth;
    private String lastActivityDate;
}