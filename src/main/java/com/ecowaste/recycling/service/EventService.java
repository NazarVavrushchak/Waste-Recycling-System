package com.ecowaste.recycling.service;

import com.ecowaste.recycling.dto.event.EventRequestDto;
import com.ecowaste.recycling.dto.event.EventResponseDto;

import java.util.List;

public interface EventService {
    EventResponseDto createEvent(EventRequestDto requestDto , Long userId);
    EventResponseDto updateEvent(EventRequestDto requestDto, Long id);
    List<EventResponseDto> getAllEvents(Long userId);
    EventResponseDto markEventAsCompleted(Long eventId);
    void deleteEvent(Long eventId);
}
