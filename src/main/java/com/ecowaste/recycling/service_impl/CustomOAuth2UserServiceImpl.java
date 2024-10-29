package com.ecowaste.recycling.service_impl;

import com.ecowaste.recycling.entity.User;
import com.ecowaste.recycling.enums.Role;
import com.ecowaste.recycling.enums.UserStatus;
import com.ecowaste.recycling.repository.UserRepo;
import com.ecowaste.recycling.service.CustomOAuth2UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomOAuth2UserServiceImpl extends OidcUserService implements CustomOAuth2UserService {
    private final UserRepo userRepo;
    private final JwtServiceImpl jwtServiceImpl;

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        return processOidcUser(oidcUser);
    }

    @Override
    @Transactional
    public OidcUser processOidcUser(OidcUser oidcUser) {
        Map<String, Object> attributes = oidcUser.getAttributes();

        String email = (String) attributes.get("email");
        if (email == null || email.isEmpty()) {
            log.error("Email is missing in OAuth2 user attributes");
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        log.info("Processing OAuth2 login for email: {}", email);

        Optional<User> existingUser = userRepo.findByEmail(email);
        if (existingUser.isPresent()) {
            log.info("User with email {} already exists", email);
            return oidcUser;
        }

        String name = (String) attributes.get("name");
        if (name == null || name.isEmpty()) {
            log.warn("Name is missing in OAuth2 user attributes for email: {}", email);
            name = "Unknown";
        }

        String refreshTokenKey = attributes.get("refresh_token_key") != null
                ? attributes.get("refresh_token_key").toString()
                : generateDefaultRefreshTokenKey();

        createUser(name, email, refreshTokenKey);
        log.info("Created new user with email: {}", email);

        return oidcUser;
    }

    private User createUser(String name, String email, String refreshTokenKey) {
        User user = User.builder()
                .username(name)
                .email(email)
                .dateOfRegistration(LocalDateTime.now())
                .lastActivityTime(LocalDateTime.now())
                .userStatus(UserStatus.ACTIVATED)
                .refreshTokenKey(refreshTokenKey)
                .role(Role.USER)//than I want add ability to change the role
                .password("default_password")
                .build();
       return  userRepo.save(user);
    }

    private String generateDefaultRefreshTokenKey() {
        return jwtServiceImpl.generateTokenKey();
    }
}
