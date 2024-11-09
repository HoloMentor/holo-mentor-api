package com.holomentor.holomentor.dto.staff;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StaffCreateDTO {
    @NotNull
    private Long instituteId;

    @NotNull
    private Long teacherId;

    @NotNull
    private Long teacherInstituteId;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Email
    @NotEmpty
    private String email;
}
