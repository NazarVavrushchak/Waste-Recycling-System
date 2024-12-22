package com.ecowaste.recycling.service;

import com.ecowaste.recycling.dto.habit.HabitDtoRequest;
import com.ecowaste.recycling.dto.habit.HabitDtoResponse;

import java.util.List;

public interface HabitService {
    HabitDtoResponse createHabit(HabitDtoRequest habitDtoRequest, Long userId);

    List<HabitDtoResponse> getHabitsByUserId(Long userId);

    HabitDtoResponse updateHabit(Long id, HabitDtoRequest request);

    HabitDtoResponse markHabitAsCompleted(Long habitId);

    long countCompletedHabitsByUser(Long userId);

    void deleteHabit(Long habitId);
}
