package com.ecowaste.recycling.entity;

import com.ecowaste.recycling.enums.EventType;
import com.ecowaste.recycling.enums.LocationType;
import com.ecowaste.recycling.enums.Thematic;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70, nullable = false)
    private String title;

    @Column(length = 5000, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Thematic thematic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType locationType;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean completed;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY
            , mappedBy = "event")
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        image.setEvent(this);
        images.add(image);
    }
}