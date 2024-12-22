package com.ecowaste.recycling.repository;

import com.ecowaste.recycling.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
    List<Event> findAllByUserId(Long userId);

    long countCompletedEventsByUserId(Long userId);

    @Query(value = "SELECT COUNT(DISTINCT DATE(e.start_time)) " +
            "FROM events e " +
            "WHERE e.user_id = :userId AND e.start_time > CURRENT_DATE - INTERVAL '30 DAYS'",
            nativeQuery = true)
    long countDistinctActiveDaysInLastMonth(@Param("userId") Long userId);

    @Query("SELECT MAX(FUNCTION('DATE', e.startTime)) FROM Event e WHERE e.user.id = :userId")
    LocalDate findLastActivityDateByUserId(@Param("userId") Long userId);
}