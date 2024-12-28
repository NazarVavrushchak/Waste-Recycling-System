package com.ecowaste.recycling.controller;

import com.ecowaste.recycling.dto.habit.HabitDtoRequest;
import com.ecowaste.recycling.dto.habit.HabitDtoResponse;
import com.ecowaste.recycling.entity.Habit;
import com.ecowaste.recycling.service.HabitService;
import com.ecowaste.recycling.service_impl.HabitServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {
    private final HabitService habitService;
    private final HabitServiceImpl habitServiceImpl;

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

    @DeleteMapping("/delete/{habitId}")
    public ResponseEntity<String> deleteHabit(@PathVariable Long habitId){
        habitService.deleteHabit(habitId);
        return ResponseEntity.ok("Habit deleted successfully");
    }
}