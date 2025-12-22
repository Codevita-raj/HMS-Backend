package com.projecth.hms.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangePasswordResponse {
    private String message;
}

