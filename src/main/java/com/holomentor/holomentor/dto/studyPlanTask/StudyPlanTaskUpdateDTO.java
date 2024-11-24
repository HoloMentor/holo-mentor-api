package com.holomentor.holomentor.dto.studyPlanTask;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyPlanTaskUpdateDTO {
    @NotNull(message = "title is required")
    private String title;
    @NotNull(message = "description is required")
    private JsonNode description;
}
