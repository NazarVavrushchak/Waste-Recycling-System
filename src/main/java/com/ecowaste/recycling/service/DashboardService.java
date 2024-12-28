package com.ecowaste.recycling.service;

import com.ecowaste.recycling.dto.dashboard.DashboardResponseDto;
import com.ecowaste.recycling.dto.dashboard.goal.GoalDtoResponse;
import com.ecowaste.recycling.dto.dashboard.goal.GoalRequestDto;
import com.ecowaste.recycling.entity.Goal;
import com.ecowaste.recycling.enums.GoalStatus;

import java.util.List;

public interface DashboardService {
    DashboardResponseDto getDashboardData(Long userId);
    void createGoal(GoalRequestDto request);
    void updateGoal(Long goalId, GoalRequestDto request);
    List<GoalDtoResponse> getUserGoals(Long userId);
    void deleteGoal(Long goalId);
}