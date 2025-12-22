package com.projecth.hms.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminCreateUserResponse {

    private Long id;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}

