package com.projecth.hms.user.controller;

import com.projecth.hms.user.dto.*;
import com.projecth.hms.user.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/set/password")
    public ResponseEntity<SetPasswordResponse> setPassword(
            @RequestBody SetPasswordRequest request) {

        return ResponseEntity.ok(passwordService.setPassword(request));
    }

    @PostMapping("/change/password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @RequestParam Long userId,
            @RequestBody ChangePasswordRequest request
    ) {
        return ResponseEntity.ok(
                passwordService.changePassword(userId, request)
        );
    }

    @PostMapping("/forgot/password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        passwordService.forgotPassword(request);
        return ResponseEntity.ok("Password reset link sent");
    }
}

