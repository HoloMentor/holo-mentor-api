package com.holomentor.holomentor.dto.studyPlanTask;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyPlanTaskCreateDTO {
    @NotNull(message = "institute ID is required")
    private Long instituteId;
    @NotNull(message = "class ID is required")
    private Long classId;
    @NotNull(message = "study plan ID is required")
    private Long studyPlanId;
    @NotNull(message = "title is required")
    private String title;
    @NotNull(message = "description is required")
    private JsonNode description;
}
