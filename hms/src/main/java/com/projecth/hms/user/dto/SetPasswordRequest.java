package com.projecth.hms.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetPasswordRequest {
    private String token;
    private String newPassword;
}

