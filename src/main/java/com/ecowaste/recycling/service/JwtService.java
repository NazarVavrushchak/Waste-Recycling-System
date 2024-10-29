package com.ecowaste.recycling.service;

import com.ecowaste.recycling.dto.UserDto;
import com.ecowaste.recycling.enums.Role;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {
    String extractUserName(String token);
    <T> T extractClaim(String token , Function<Claims, T> claimsResolver);
    String generateToken(String email , Role role);
    String generateRefreshToken(UserDto userDto);
    boolean isTokenValid(String token, String tokenKey);
    String getTokenFromHttpServletRequest(HttpServletRequest servletRequest);
    String getAccessTokenKey();
    String getEmailOutOfAccessToken(String token);
    String generateTokenKey();
}
