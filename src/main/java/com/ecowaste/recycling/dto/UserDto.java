package com.ecowaste.recycling.dto;

import com.ecowaste.recycling.enums.EmailNotification;
import com.ecowaste.recycling.enums.Role;
import com.ecowaste.recycling.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserDto {
    private Long id;

    private String name;

    private String email;

    private Role role;

    private UserStatus userStatus;

    private EmailNotification emailNotification;

    private LocalDateTime dateOfRegistration;

    private String refreshTokenKey;

    private LocalDateTime lastActivityTime;

    private String profilePicturePath;

    private String city;
}
