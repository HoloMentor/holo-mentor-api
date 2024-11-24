package com.holomentor.holomentor.dto.student;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentCreateDTO {
    @NotNull(message = "first name is required")
    private String firstName;
    @NotNull(message = "last name is required")
    private String lastName;
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "registration is required")
    private String registrationNo;
    private Long classId;
    private Long instituteId;
}
