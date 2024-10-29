package com.ecowaste.recycling.repository;

import com.ecowaste.recycling.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface EventRepo extends JpaRepository<Event , Long> {
    List<Event> findAllByUserId(Long userId);
    long countEventsByUserId(Long userId);
    long countCompletedEventsByUserId(Long userId);
}