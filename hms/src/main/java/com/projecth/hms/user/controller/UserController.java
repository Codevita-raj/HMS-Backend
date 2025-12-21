package com.projecth.hms.user.controller;

import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final CurrentUserService currentUserService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        User user = currentUserService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
}

