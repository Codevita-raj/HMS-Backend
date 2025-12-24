package com.projecth.hms.user.service;

import com.projecth.hms.auth.service.RefreshTokenService;
import com.projecth.hms.shared.enums.UserStatus;
import com.projecth.hms.shared.notifications.EmailService;
import com.projecth.hms.user.dto.*;
import com.projecth.hms.user.entity.PasswordResetToken;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.repository.PasswordResetTokenRepository;
import com.projecth.hms.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public SetPasswordResponse setPassword(SetPasswordRequest request) {

        PasswordResetToken token = tokenRepository
                .findByTokenAndUsedFalse(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or already used token"));

        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setUpdatedAt(LocalDateTime.now());

        token.setUsed(true);
        refreshTokenService.revokeAllUserTokens(user);
        userRepository.save(user);
        tokenRepository.save(token);

        String username = user.getEmail();
        String message = "Your password was successfully changed.";
        String actionLink = "";

        emailService.sendEmail(
                user.getEmail(),
                "HSMS App - Password Changed",
                username,
                message,
                actionLink
        );

        return SetPasswordResponse.builder()
                .message("Password set successfully")
                .build();
    }

@Transactional
    public ChangePasswordResponse changePassword(
            Long userId,
            ChangePasswordRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        refreshTokenService.revokeAllUserTokens(user);
        userRepository.save(user);

        String username = user.getEmail();
        String message = "Your password was successfully changed.";
        String actionLink = "";

        emailService.sendEmail(
                user.getEmail(),
                "HSMS App - Password Changed",
                username,
                message,
                actionLink
        );

        return ChangePasswordResponse.builder()
                .message("Password changed successfully")
                .build();
    }

    public void forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUserId(user.getId());
        token.setExpiryTime(LocalDateTime.now().plusHours(24));

        tokenRepository.save(token);

        String username = user.getEmail();
        String message = "Welcome to HSMS App. Please, Reset your HMS password using the link below:";
        String actionLink ="http://localhost:8080/set-password?token=" + token.getToken();

        emailService.sendEmail(
                user.getEmail(),
                "HSMS App - Password Changed",
                username,
                message,
                actionLink
        );

    }
}

