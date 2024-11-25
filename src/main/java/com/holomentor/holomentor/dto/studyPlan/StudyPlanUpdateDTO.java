package com.holomentor.holomentor.dto.studyPlan;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyPlanUpdateDTO {
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "description is required")
    private String description;
}
