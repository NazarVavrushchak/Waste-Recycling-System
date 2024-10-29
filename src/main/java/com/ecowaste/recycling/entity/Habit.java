package com.ecowaste.recycling.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(nullable = false , length = 70)
    private String title;

    @Column(nullable = false , columnDefinition = "TEXT")
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean completed;
}
