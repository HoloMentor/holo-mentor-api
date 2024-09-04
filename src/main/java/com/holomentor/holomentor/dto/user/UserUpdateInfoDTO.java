package com.holomentor.holomentor.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateInfoDTO {
    @NotNull(message = "country is required")
    private String country;
    @NotNull(message = "country code is required")
    private String countryCode;
    @NotNull(message = "contact number is required")
    private String contactNumber;
}
