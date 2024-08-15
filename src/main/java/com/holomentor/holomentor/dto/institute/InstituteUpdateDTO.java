package com.holomentor.holomentor.dto.institute;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class InstituteUpdateDTO {
    @NotNull(message = "institute name is required")
    private String name;
    @NotNull(message = "institute city is required")
    private String city;
    @NotNull(message = "institute address is required")
    private String address;
    @NotNull(message = "institute registration number is required")
    private String registrationNumber;
    @NotNull(message = "institute established date is required")
    private Date establishedDate;
}
