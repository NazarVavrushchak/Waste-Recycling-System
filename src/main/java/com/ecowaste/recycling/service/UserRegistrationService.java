package com.ecowaste.recycling.service;

import com.ecowaste.recycling.dto.registration.SignInRequestDto;
import com.ecowaste.recycling.dto.registration.SignUpRequestDto;
import com.ecowaste.recycling.dto.registration.SuccessSignInDto;
import com.ecowaste.recycling.dto.registration.SuccessSignUpDto;

public interface UserRegistrationService {
    SuccessSignUpDto signUp(SignUpRequestDto dto);
    SuccessSignInDto signIn(SignInRequestDto dto , String email);
}
