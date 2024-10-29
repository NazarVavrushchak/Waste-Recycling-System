package com.ecowaste.recycling.service_impl;

import com.ecowaste.recycling.dto.habit.HabitDtoRequest;
import com.ecowaste.recycling.dto.habit.HabitDtoResponse;
import com.ecowaste.recycling.entity.Habit;
import com.ecowaste.recycling.entity.User;
import com.ecowaste.recycling.repository.HabitRepo;
import com.ecowaste.recycling.repository.UserRepo;
import com.ecowaste.recycling.service.HabitService;
import com.ecowaste.recycling.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class HabitServiceImpl implements HabitService {
    private final HabitRepo habitRepo;
    private final UserRepo userRepo;

    @Override
    @SneakyThrows
    public HabitDtoResponse createHabit(HabitDtoRequest request, Long userId) {
        User user = userRepo.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Habit habit = Habit.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .tags(new HashSet<>(request.getTags()))
                .difficulty(request.getDifficulty())
                .user(user)
                .createdAt(LocalDateTime.now())
                .completed(false)
                .build();

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            String imageUrl = FileUploadUtil.saveFile(request.getImage());
            habit.setImageUrl(imageUrl);
        }

        habitRepo.save(habit);

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

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            String imageUrl = FileUploadUtil.saveFile(request.getImage());
            habit.setImageUrl(imageUrl);
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

    private HabitDtoResponse mapToDto(Habit habit) {
        return HabitDtoResponse.builder()
                .id(habit.getId())
                .title(habit.getTitle())
                .description(habit.getDescription())
                .tags(habit.getTags())
                .difficulty(habit.getDifficulty())
                .imageUrl(habit.getImageUrl())
                .createdAt(habit.getCreatedAt())
                .completed(habit.isCompleted())
                .build();
    }
}
//on front part should to add calendar and duration of that habbit