package com.projecth.hms.auth.dto;

import com.projecth.hms.shared.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateUserRequest {

    private String email;
    private String phone;
    private Role role;
}

