package com.ecowaste.recycling.service_impl;

import com.ecowaste.recycling.dto.UserDto;
import com.ecowaste.recycling.dto.registration.SignInRequestDto;
import com.ecowaste.recycling.dto.registration.SignUpRequestDto;
import com.ecowaste.recycling.dto.registration.SuccessSignInDto;
import com.ecowaste.recycling.dto.registration.SuccessSignUpDto;
import com.ecowaste.recycling.entity.User;
import com.ecowaste.recycling.enums.Role;
import com.ecowaste.recycling.repository.UserRepo;
import com.ecowaste.recycling.service.JwtService;
import com.ecowaste.recycling.service.UserRegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public SuccessSignUpDto signUp(SignUpRequestDto dto) {
        if(userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .dateOfRegistration(LocalDateTime.now())
                .lastActivityTime(LocalDateTime.now())
                .build();

        userRepo.save(user);

        return SuccessSignUpDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @Override
    public SuccessSignInDto signIn(SignInRequestDto dto , String email) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        UserDto userDto = UserDto.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .refreshTokenKey(jwtService.generateTokenKey())
                .build();

        String accessToken = jwtService.generateToken(email , Role.USER);
        String refreshToken = jwtService.generateRefreshToken(userDto);

        return SuccessSignInDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}