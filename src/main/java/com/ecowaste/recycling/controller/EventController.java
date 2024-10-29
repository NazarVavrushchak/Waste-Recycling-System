package com.ecowaste.recycling.controller;

import com.ecowaste.recycling.dto.event.EventRequestDto;
import com.ecowaste.recycling.dto.event.EventResponseDto;
import com.ecowaste.recycling.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<EventResponseDto> createEvent(@ModelAttribute EventRequestDto requestDto
            , @RequestParam Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(requestDto, userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EventResponseDto>> getAllEvent(@RequestParam Long userId) {
        return ResponseEntity.ok().body(eventService.getAllEvents(userId));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@RequestParam Long id
            , @RequestBody EventRequestDto requestDto){
        EventResponseDto updatedEvent = eventService.updateEvent(requestDto, id);
        return ResponseEntity.ok(updatedEvent);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<EventResponseDto> markEventAsCompleted(@PathVariable Long id) {
        EventResponseDto completedEvent = eventService.markEventAsCompleted(id);
        return ResponseEntity.ok(completedEvent);
    }
}
