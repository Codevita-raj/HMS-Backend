package com.projecth.hms.user.controller;

import com.projecth.hms.user.dto.AdminCreateUserRequest;
import com.projecth.hms.user.dto.AdminCreateUserResponse;
import com.projecth.hms.user.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users")
    public ResponseEntity<AdminCreateUserResponse> createUser(
            @RequestBody AdminCreateUserRequest request) {

        return ResponseEntity.ok(adminUserService.createUser(request));
    }
}

