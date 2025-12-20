package com.projecth.hms.auth.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private Long id;
    private String email;
    private String phone;
    private String role;
    private String status;
    private LocalDateTime createdAt;
}
