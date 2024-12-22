package com.ecowaste.recycling.entity;

import com.ecowaste.recycling.enums.GoalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "goals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String description;

    @Enumerated(EnumType.STRING)
    private GoalStatus status;

    private LocalDate targetDate;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}