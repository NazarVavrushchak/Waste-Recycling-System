package com.ecowaste.recycling.service_impl;

import com.ecowaste.recycling.dto.dashboard.DashboardResponseDto;
import com.ecowaste.recycling.entity.User;
import com.ecowaste.recycling.repository.EventRepo;
import com.ecowaste.recycling.repository.HabitRepo;
import com.ecowaste.recycling.repository.UserRepo;
import com.ecowaste.recycling.service.DashboardService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final UserRepo userRepo;
    private final HabitRepo habitRepo;
    private final EventRepo eventRepo;

    @Override
    public DashboardResponseDto getDashboardData(Long userId) {
        User user = userRepo.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID :" + userId));

        long completedHabits = eventRepo.countCompletedEventsByUserId(userId);
        long eventsParticipated = habitRepo.countCompletedHabitsByUserId(userId);

        return DashboardResponseDto.builder()
                .userName(user.getUsername())
                .completedHabits(completedHabits)
                .eventsParticipated(eventsParticipated)
                .build();
    }
}
