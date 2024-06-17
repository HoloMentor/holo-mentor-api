package com.holomentor.holomentor.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserLoginDTO {
    @NotNull(message = "username is required")
    private String username;
    @NotNull(message = "password is required")
    private String password;
}
