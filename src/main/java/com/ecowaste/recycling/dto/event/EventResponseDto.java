package com.ecowaste.recycling.dto.event;

import com.ecowaste.recycling.enums.EventType;
import com.ecowaste.recycling.enums.LocationType;
import com.ecowaste.recycling.enums.Thematic;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventResponseDto {
    private Long id;
    private String title;
    private Thematic thematic;
    private EventType eventType;
    private String description;
    private LocalDateTime startTime;
    private LocationType locationType;
    private String userFullName;
    private List<String> images;;
    private Boolean completed;
}