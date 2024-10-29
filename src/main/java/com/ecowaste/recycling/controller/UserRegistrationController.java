package com.ecowaste.recycling.controller;

import com.ecowaste.recycling.dto.registration.SignInRequestDto;
import com.ecowaste.recycling.dto.registration.SignUpRequestDto;
import com.ecowaste.recycling.dto.registration.SuccessSignInDto;
import com.ecowaste.recycling.dto.registration.SuccessSignUpDto;
import com.ecowaste.recycling.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRegistration")
@RequiredArgsConstructor
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;

    @PostMapping("/signUp")
    public ResponseEntity<SuccessSignUpDto> signUp(@RequestBody SignUpRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistrationService.signUp(dto));
    }

    @PostMapping("/signIn")
    public ResponseEntity<SuccessSignInDto> signIn(@RequestBody SignInRequestDto dto , String email) {
        return ResponseEntity.ok(userRegistrationService.signIn(dto , email));
    }
}
