package com.projecth.hms.auth.controller;

import com.projecth.hms.auth.dto.*;
import com.projecth.hms.auth.service.AuthService;
import com.projecth.hms.shared.utill.JwtUtil;
import com.projecth.hms.auth.service.RefreshTokenService;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        LoginResponse saved = authService.login(loginRequest);

        ResponseCookie refreshCookie = ResponseCookie.from(
                        "refreshToken",
                        saved.getRefreshToken()
                )
                .httpOnly(true)
                .secure(false) // true in prod (HTTPS)
                .path("/api/v1/auth")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        // IMPORTANT: never expose refresh token
        saved.setRefreshToken(null);

        return ResponseEntity.ok(saved);
    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        RefreshTokenResponse tokens =
                refreshTokenService.refreshAccessToken(refreshToken);

        ResponseCookie refreshCookie = ResponseCookie.from(
                        "refreshToken",
                        tokens.getRefreshToken()
                )
                .httpOnly(true)
                .secure(false)
                .path("/api/v1/auth")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        // NEVER expose refresh token in body
        tokens.setRefreshToken(null);

        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken != null) {
            refreshTokenService.revokeByToken(refreshToken);
        }

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .path("/api/v1/auth")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.ok("Logged out successfully");
    }




}
