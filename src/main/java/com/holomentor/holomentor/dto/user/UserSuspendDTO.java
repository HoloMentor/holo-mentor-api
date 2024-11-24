package com.holomentor.holomentor.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSuspendDTO {
    @NotNull(message = "blacklist state is required")
    private Boolean isBlacklisted;
}
