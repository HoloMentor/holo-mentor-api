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
public class StudyPlanCreateDTO {
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "description is required")
    private String description;
    @NotNull(message = "institute ID is required")
    private Long instituteId;
    @NotNull(message = "class ID is required")
    private Long classId;
    @NotNull(message = "tier number is required")
    private Integer tier;
    @NotNull(message = "tasks are required")
    @NotEmpty(message = "at least one task is required")
    private List<StudyPlanCreateTaskDTO> tasks;
}
