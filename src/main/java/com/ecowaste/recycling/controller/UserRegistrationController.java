package com.ecowaste.recycling.controller;

import com.ecowaste.recycling.dto.registration.SignInRequestDto;
import com.ecowaste.recycling.dto.registration.SignUpRequestDto;
import com.ecowaste.recycling.dto.registration.SuccessSignInDto;
import com.ecowaste.recycling.dto.registration.SuccessSignUpDto;
import com.ecowaste.recycling.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userRegistration")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "signup"; // Повинен бути файл signup.html у resources/templates
    }

    @GetMapping("/signIn")
    public String getSignInPage() {
        return "signin"; // Повинен бути файл signin.html у resources/templates
    }

    @PostMapping("/signUp")
    public ResponseEntity<SuccessSignUpDto> signUp(@RequestBody SignUpRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistrationService.signUp(dto));
    }

    @PostMapping("/signIn")
    public ResponseEntity<SuccessSignInDto> signIn(@RequestBody SignInRequestDto dto , String email) {
        return ResponseEntity.ok(userRegistrationService.signIn(dto , email));
    }
}