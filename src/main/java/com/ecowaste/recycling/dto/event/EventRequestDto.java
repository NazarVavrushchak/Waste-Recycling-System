package com.ecowaste.recycling.dto.event;

import com.ecowaste.recycling.enums.EventType;
import com.ecowaste.recycling.enums.LocationType;
import com.ecowaste.recycling.enums.Thematic;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class EventRequestDto {
    private String title;
    private Thematic thematic;
    private EventType eventType;
    private String description;
    private LocalDateTime startTime;
    private LocationType locationType;
    private MultipartFile image;
    private Long userId;
    private Boolean completed;
}