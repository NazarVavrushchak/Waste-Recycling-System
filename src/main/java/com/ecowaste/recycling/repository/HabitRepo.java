package com.ecowaste.recycling.repository;

import com.ecowaste.recycling.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HabitRepo extends JpaRepository<Habit, Long> {
    List<Habit> findAllByUserId(Long userId);

    @Query("SELECT COUNT(h) FROM Habit h WHERE h.user.id = :userId AND h.completed = true")
    long countCompletedHabitsByUserId(@Param("userId") Long userId);

    @Query("SELECT h FROM Habit h WHERE h.startDate >= :startDate AND h.endDate <= :endDate")
    List<Habit> findHabitsWithinDateRange(LocalDateTime startDate, LocalDateTime endDate);
}