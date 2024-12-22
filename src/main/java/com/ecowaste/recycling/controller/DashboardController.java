package com.ecowaste.recycling.controller;

import com.ecowaste.recycling.dto.dashboard.DashboardResponseDto;
import com.ecowaste.recycling.entity.Goal;
import com.ecowaste.recycling.enums.GoalStatus;
import com.ecowaste.recycling.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/show-statistic")
    public ResponseEntity<DashboardResponseDto> getAchievements(@RequestParam Long userId) {
        return ResponseEntity.ok().body(dashboardService.getDashboardData(userId));
    }

    @PostMapping("/goals")
    public ResponseEntity<String> createGoal(
            @RequestParam Long userId,
            @RequestParam String description,
            @RequestParam(required = false) String targetDate
    ) {
        dashboardService.createGoal(userId, description, targetDate);
        return ResponseEntity.ok("Goal created successfully");
    }

    @PutMapping("/goals/{goalId}")
    public ResponseEntity<String> updateGoal(
            @PathVariable Long goalId,
            @RequestParam String description,
            @RequestParam(required = false) String targetDate,
            @RequestParam GoalStatus status
    ) {
        dashboardService.updateGoal(goalId, description, targetDate, status);
        return ResponseEntity.ok("Goal updated successfully");
    }

    @GetMapping("/goals")
    public ResponseEntity<List<Goal>> getUserGoals(@RequestParam Long userId) {
        return ResponseEntity.ok(dashboardService.getUserGoals(userId));
    }

    @DeleteMapping("/goals/{goalId}")
    public ResponseEntity<String> deleteGoal(@PathVariable Long goalId) {
        dashboardService.deleteGoal(goalId);
        return ResponseEntity.ok("Goal deleted successfully");
    }
}