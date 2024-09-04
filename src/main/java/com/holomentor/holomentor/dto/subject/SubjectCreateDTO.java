package com.holomentor.holomentor.dto.subject;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectCreateDTO {
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "institute ID is required")
    private Long instituteId;
}
