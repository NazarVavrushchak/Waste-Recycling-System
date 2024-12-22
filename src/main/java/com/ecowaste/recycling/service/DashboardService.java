package com.ecowaste.recycling.service;

import com.ecowaste.recycling.dto.dashboard.DashboardResponseDto;
import com.ecowaste.recycling.entity.Goal;
import com.ecowaste.recycling.enums.GoalStatus;

import java.util.List;

public interface DashboardService {
    DashboardResponseDto getDashboardData(Long userId);
    void createGoal(Long userId, String description, String targetDate);
    void updateGoal(Long goalId, String description, String targetDate, GoalStatus status);
    List<Goal> getUserGoals(Long userId);
    void deleteGoal(Long goalId);
}