package com.ecowaste.recycling.service_impl;

import com.ecowaste.recycling.dto.event.EventRequestDto;
import com.ecowaste.recycling.dto.event.EventResponseDto;
import com.ecowaste.recycling.entity.Event;
import com.ecowaste.recycling.entity.User;
import com.ecowaste.recycling.repository.EventRepo;
import com.ecowaste.recycling.repository.UserRepo;
import com.ecowaste.recycling.service.EventService;
import com.ecowaste.recycling.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepo eventRepo;
    private final UserRepo userRepo;

    @Override
    @SneakyThrows
    public EventResponseDto createEvent(EventRequestDto requestDto , Long userid) {
        User user = userRepo.findById(Math.toIntExact(userid))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + requestDto.getUserId()));

        Event event = Event.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .thematic(requestDto.getThematic())
                .eventType(requestDto.getEventType())
                .locationType(requestDto.getLocationType())
                .startTime(requestDto.getStartTime())
                .user(user)
                .completed(requestDto.getCompleted() != null ? requestDto.getCompleted() : false)
                .build();

        if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {
            String imageUrl = FileUploadUtil.saveFile(requestDto.getImage());
            event.setImageUrl(imageUrl);
        }

        event = eventRepo.save(event);

        return mapToDto(event);
    }

    @Override
    @SneakyThrows
    public EventResponseDto updateEvent(EventRequestDto requestDto, Long id) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not dound"));
        if (requestDto.getTitle() != null) {
            event.setTitle(requestDto.getTitle());
        }
        if (requestDto.getDescription() != null) {
            event.setDescription(requestDto.getDescription());
        }
        if (requestDto.getThematic() != null) {
            event.setThematic(requestDto.getThematic());
        }
        if (requestDto.getEventType() != null) {
            event.setEventType(requestDto.getEventType());
        }
        if (requestDto.getLocationType() != null) {
            event.setLocationType(requestDto.getLocationType());
        }
        if (requestDto.getStartTime() != null) {
            event.setStartTime(requestDto.getStartTime());
        }

        if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {
            String imageUrl = FileUploadUtil.saveFile(requestDto.getImage());
            event.setImageUrl(imageUrl);
        }

        if (requestDto.getCompleted() != null) {
            event.setCompleted(requestDto.getCompleted());
        }

        event = eventRepo.save(event);

        return mapToDto(event);
    }

    @Override
    public List<EventResponseDto> getAllEvents(Long userId) {
        List<Event> allUserEvents = eventRepo.findAllByUserId(userId);
        return allUserEvents.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public EventResponseDto markEventAsCompleted(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + eventId));

        event.setCompleted(true);
        event = eventRepo.save(event);

        return mapToDto(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID :" + eventId));

        eventRepo.delete(event);
    }

    private EventResponseDto mapToDto(Event event) {
        return EventResponseDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .thematic(event.getThematic())
                .eventType(event.getEventType())
                .startTime(event.getStartTime())
                .description(event.getDescription())
                .locationType(event.getLocationType())
                .imageUrl(event.getImageUrl())
                .userFullName(event.getUser().getUsername())
                .completed(event.isCompleted())
                .build();
    }
}