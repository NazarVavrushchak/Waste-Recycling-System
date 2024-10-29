package com.ecowaste.recycling.dto.registration;

import com.ecowaste.recycling.constant.ValidationConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDto {
    @Pattern(
            regexp = ValidationConstant.USERNAME_REGEX,
            message = ValidationConstant.USERNAME_MESSAGE
    )
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(
            regexp = ValidationConstant.PASSWORD_VALIDATION,
            message = ValidationConstant.PASSWORD_MESSAGE
    )
    private String password;
}
