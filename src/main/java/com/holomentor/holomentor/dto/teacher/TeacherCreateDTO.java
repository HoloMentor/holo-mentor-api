package com.holomentor.holomentor.dto.teacher;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeacherCreateDTO {
    private Long instituteId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer registrationNumber;
}
