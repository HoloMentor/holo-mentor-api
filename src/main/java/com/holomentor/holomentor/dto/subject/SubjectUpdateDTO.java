package com.holomentor.holomentor.dto.subject;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectUpdateDTO {
    @NotNull(message = "name is required")
    private String name;
}
