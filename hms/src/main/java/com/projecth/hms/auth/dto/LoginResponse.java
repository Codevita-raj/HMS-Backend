package com.projecth.hms.auth.dto;

import com.projecth.hms.shared.enums.Role;
import com.projecth.hms.shared.enums.UserStatus;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String email;
    private Role role;
    private UserStatus status;
    private String tokenType = "Bearer";
    private String token;
    private String refreshToken;
}
