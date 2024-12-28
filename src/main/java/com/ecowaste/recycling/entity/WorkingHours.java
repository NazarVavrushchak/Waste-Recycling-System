package com.ecowaste.recycling.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WorkingHours {
    @Temporal(TemporalType.TIME)
    @NotNull
    private LocalTime openingTime;

    @Temporal(TemporalType.TIME)
    @NotNull
    private LocalTime closingTime;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "recycling_point_working_days", joinColumns = @JoinColumn(name = "recycling_point_id"))
    private Set<DayOfWeek> workingDays;

    @AssertTrue(message = "Closing time must be after opening time")
    public boolean isValidWorkingHours() {
        return openingTime.isBefore(closingTime);
    }
}