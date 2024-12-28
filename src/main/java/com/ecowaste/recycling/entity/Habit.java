package com.ecowaste.recycling.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "habits")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 70)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer difficulty;

    @ElementCollection
    @CollectionTable(
            name = "habit_tags",
            joinColumns = @JoinColumn(name = "habit_id")
    )
    @Column(name = "tag")
    private Set<String> tags;

    private String imageUrl;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(name = "duration_in_days", nullable = false)
    private int durationInDays;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean completed;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY
            , mappedBy = "habit" ,  orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        image.setHabit(this);
        images.add(image);
    }
}