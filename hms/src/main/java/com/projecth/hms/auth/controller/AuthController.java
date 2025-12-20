package com.projecth.hms.auth.controller;

import com.projecth.hms.auth.dto.LoginRequest;
import com.projecth.hms.auth.dto.LoginResponse;
import com.projecth.hms.auth.dto.RegisterRequest;
import com.projecth.hms.auth.dto.RegisterResponse;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.auth.service.AuthService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResponse saved =  authService.registerUser(registerRequest);
        return ResponseEntity.ok(saved);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse saved = authService.login(loginRequest);
        return ResponseEntity.ok(saved);
    }
}
