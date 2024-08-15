package com.holomentor.holomentor.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthInvitationAcceptDTO {
    @NotNull(message = "reset value is required")
    private Boolean reset;
    @NotNull(message = "password is required")
    private String password;
    @NotNull(message = "invitation token is required")
    private String token;
}
