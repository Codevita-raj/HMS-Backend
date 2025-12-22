package com.projecth.hms.auth.service;

import com.projecth.hms.auth.dto.AdminCreateUserRequest;
import com.projecth.hms.auth.dto.AdminCreateUserResponse;
import com.projecth.hms.security.validator.RoleValidator;
import com.projecth.hms.shared.enums.UserStatus;
import com.projecth.hms.shared.notifications.EmailService;
import com.projecth.hms.user.entity.PasswordResetToken;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.repository.PasswordResetTokenRepository;
import com.projecth.hms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final RoleValidator roleValidator;
    private final EmailService emailService;

    private User getCurrentAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }


    public AdminCreateUserResponse createUser(AdminCreateUserRequest request) {

        User admin = getCurrentAdmin();

//        roleValidator.requireAdmin(admin);
        roleValidator.validateAdminCreation(admin, request.getRole());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRole())
                .status(UserStatus.INACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .password("TEMP")
                .build();

        User savedUser = userRepository.save(user);

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUserId(savedUser.getId());
        token.setExpiryTime(LocalDateTime.now().plusHours(24));

        tokenRepository.save(token);

        emailService.sendEmail(
                savedUser.getEmail(),
                "Set your HSMS password",
                savedUser.getEmail(),
                "You have been added to HSMS App. Please set your password using the link below:",
                "http://localhost:8080/set-password?token=" + token.getToken()
        );

        return AdminCreateUserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
}

