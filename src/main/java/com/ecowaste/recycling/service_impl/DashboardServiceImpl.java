package com.ecowaste.recycling.service_impl;

import com.ecowaste.recycling.dto.dashboard.DashboardResponseDto;
import com.ecowaste.recycling.dto.dashboard.goal.GoalDtoResponse;
import com.ecowaste.recycling.dto.dashboard.goal.GoalRequestDto;
import com.ecowaste.recycling.entity.Goal;
import com.ecowaste.recycling.entity.User;
import com.ecowaste.recycling.enums.GoalStatus;
import com.ecowaste.recycling.repository.EventRepo;
import com.ecowaste.recycling.repository.GoalRepo;
import com.ecowaste.recycling.repository.HabitRepo;
import com.ecowaste.recycling.repository.UserRepo;
import com.ecowaste.recycling.service.DashboardService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final UserRepo userRepo;
    private final HabitRepo habitRepo;
    private final EventRepo eventRepo;
    private final GoalRepo goalRepo;

    @Override
    public DashboardResponseDto getDashboardData(Long userId) {
        User user = userRepo.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID :" + userId));

        long completedHabits = eventRepo.countCompletedEventsByUserId(userId);
        long eventsParticipated = habitRepo.countCompletedHabitsByUserId(userId);

        List<Goal> goals = goalRepo.findByUserId(userId);
        String currentGoal = goals.stream()
                .filter(goal -> goal.getStatus() == GoalStatus.IN_PROGRESS)
                .map(Goal::getDescription)
                .findFirst()
                .orElse("No current goal");

        long daysActiveLastMonth = eventRepo.countDistinctActiveDaysInLastMonth(userId);
        LocalDate lastActivityDate = eventRepo.findLastActivityDateByUserId(userId);

        return DashboardResponseDto.builder()
                .userName(user.getUsername())
                .completedHabits(completedHabits)
                .eventsParticipated(eventsParticipated)
                .currentGoal(currentGoal)
                .daysActiveLastMonth(daysActiveLastMonth)
                .lastActivityDate(lastActivityDate != null ? lastActivityDate.toString() : "No activity recorded")
                .build();
    }

    @Override
    public void createGoal(GoalRequestDto request) {
        User user = userRepo.findById(Math.toIntExact(request.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID :" + request.getUserId()));

        Goal goal = Goal.builder()
                .user(user)
                .description(request.getDescription())
                .status(GoalStatus.IN_PROGRESS)
                .targetDate(request.getTargetDate() != null ? LocalDate.parse(request.getTargetDate()) : null)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        goalRepo.save(goal);
    }

    @Override
    public void updateGoal(Long goalId, GoalRequestDto request) {
        Goal goal = goalRepo.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with ID :" + goalId));

        goal.setDescription(request.getDescription());
        goal.setStatus(request.getStatus());
        goal.setTargetDate(request.getTargetDate() != null ? LocalDate.parse(request.getTargetDate()) : goal.getTargetDate());
        goal.setUpdatedAt(LocalDate.now());

        goalRepo.save(goal);
    }

    public List<GoalDtoResponse> getUserGoals(Long userId) {
        return goalRepo.findByUserId(userId).stream()
                .map(goal -> GoalDtoResponse.builder()
                        .id(goal.getId())
                        .description(goal.getDescription())
                        .status(goal.getStatus())
                        .targetDate(goal.getTargetDate() != null ? goal.getTargetDate().toString() : null)
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteGoal(Long goalId) {
        Goal goal = goalRepo.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with ID :" + goalId));

        goalRepo.delete(goal);
    }
}