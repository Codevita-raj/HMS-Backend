package com.projecth.hms.auth.dto;

import com.projecth.hms.shared.enums.Role;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
    private Role role;
}
