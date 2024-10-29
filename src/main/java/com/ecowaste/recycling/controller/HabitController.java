package com.ecowaste.recycling.controller;

import com.ecowaste.recycling.dto.habit.HabitDtoRequest;
import com.ecowaste.recycling.dto.habit.HabitDtoResponse;
import com.ecowaste.recycling.service.HabitService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
@AllArgsConstructor
public class HabitController {
    private final HabitService habitService;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<String> createHabit(@Valid @ModelAttribute HabitDtoRequest request) {
        habitService.createHabit(request, request.getUserId());
        return ResponseEntity.ok("Habit successfully created!");
    }

    @GetMapping("/user-habits")
    public ResponseEntity<List<HabitDtoResponse>> getHabitsByUserId(@RequestParam Long userId) {
        List<HabitDtoResponse> habits = habitService.getHabitsByUserId(userId);
        return ResponseEntity.ok(habits);
    }

    @PatchMapping(value = "/habit/update/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<HabitDtoResponse> updateHabit(
            @PathVariable Long id,
            @ModelAttribute HabitDtoRequest request) {
        HabitDtoResponse updatedHabit = habitService.updateHabit(id, request);
        return ResponseEntity.ok(updatedHabit);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<HabitDtoResponse> markEventAsCompleted(@PathVariable Long id) {
        HabitDtoResponse completedEvent = habitService.markHabitAsCompleted(id);
        return ResponseEntity.ok(completedEvent);
    }
}