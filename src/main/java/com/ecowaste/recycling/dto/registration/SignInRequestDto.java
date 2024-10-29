package com.ecowaste.recycling.dto.registration;

import com.ecowaste.recycling.constant.ValidationConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequestDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = ValidationConstant.PASSWORD_VALIDATION)
    private String password;
}
