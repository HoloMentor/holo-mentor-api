package com.holomentor.holomentor.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthLoginDTO {
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "password is required")
    private String password;
    @NotNull(message = "users institute ID is required")
    private Long userInstituteID;
}
