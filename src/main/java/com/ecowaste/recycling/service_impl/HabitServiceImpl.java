package com.ecowaste.recycling.service_impl;

import com.ecowaste.recycling.dto.habit.HabitDtoRequest;
import com.ecowaste.recycling.dto.habit.HabitDtoResponse;
import com.ecowaste.recycling.entity.Habit;
import com.ecowaste.recycling.entity.Image;
import com.ecowaste.recycling.entity.User;
import com.ecowaste.recycling.repository.HabitRepo;
import com.ecowaste.recycling.repository.UserRepo;
import com.ecowaste.recycling.service.HabitService;
import com.ecowaste.recycling.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class HabitServiceImpl implements HabitService {
    private final HabitRepo habitRepo;
    private final UserRepo userRepo;
    private final ImageService imageService;

    @Override
    @SneakyThrows
    public HabitDtoResponse createHabit(HabitDtoRequest request, Long userId) {
        User user = userRepo.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        int durationInDays = (int) java.time.Duration.between(request.getStartDate(), request.getEndDate()).toDays();

        Habit habit = Habit.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .tags(new HashSet<>(request.getTags()))
                .difficulty(request.getDifficulty())
                .user(user)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .durationInDays(durationInDays)
                .createdAt(LocalDateTime.now())
                .completed(false)
                .build();

        habit = habitRepo.save(habit);

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            Image image = imageService.saveImage(request.getImage(), habit);
            habit.addImage(image);
            habitRepo.save(habit);
        }

        return mapToDto(habit);
    }

    @Override
    public List<HabitDtoResponse> getHabitsByUserId(Long userId) {
        List<Habit> habits = habitRepo.findAllByUserId(userId);
        return habits.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @SneakyThrows
    public HabitDtoResponse updateHabit(Long id, HabitDtoRequest request) {
        Habit habit = habitRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habit not found with ID: " + id));

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            habit.setTitle(request.getTitle());
        }
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            habit.setDescription(request.getDescription());
        }
        if (request.getDifficulty() != null) {
            habit.setDifficulty(request.getDifficulty());
        }
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            habit.setTags(new HashSet<>(request.getTags()));
        }
        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getEndDate().isBefore(request.getStartDate())) {
                throw new IllegalArgumentException("End date cannot be before start date");
            }
            habit.setStartDate(request.getStartDate());
            habit.setEndDate(request.getEndDate());
            habit.setDurationInDays((int) java.time.Duration.between(request.getStartDate(), request.getEndDate()).toDays());
        }
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            Image image = imageService.saveImage(request.getImage(), habit);
            habit.addImage(image);
        }
        if (request.isCompleted() != habit.isCompleted()) {
            habit.setCompleted(request.isCompleted());
        }

        habit = habitRepo.save(habit);

        return mapToDto(habit);
    }


    @Override
    public HabitDtoResponse markHabitAsCompleted(Long habitId) {
        Habit habit = habitRepo.findById(habitId)
                .orElseThrow(() -> new IllegalArgumentException("Habit not found"));

        habit.setCompleted(true);
        habit = habitRepo.save(habit);

        return mapToDto(habit);
    }

    @Override
    public long countCompletedHabitsByUser(Long userId) {
        return habitRepo.countCompletedHabitsByUserId(userId);
    }

    @Override
    public void deleteHabit(Long habitId) {
        Habit habit = habitRepo.findById(habitId)
                .orElseThrow(() -> new IllegalArgumentException("Habit not found with ID" + habitId));

        habitRepo.delete(habit);
    }

    private HabitDtoResponse mapToDto(Habit habit) {
        List<String> imageUrls = habit.getImages() == null ?
                new ArrayList<>() :
                habit.getImages().stream()
                        .map(image -> "/images/" + image.getId())
                        .collect(Collectors.toList());

        return HabitDtoResponse.builder()
                .id(habit.getId())
                .title(habit.getTitle())
                .description(habit.getDescription())
                .tags(habit.getTags())
                .difficulty(habit.getDifficulty())
                .images(imageUrls)
                .createdAt(habit.getCreatedAt())
                .completed(habit.isCompleted())
                .startDate(habit.getStartDate())
                .endDate(habit.getEndDate())
                .durationInDays(habit.getDurationInDays())
                .build();
    }
}