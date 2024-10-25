package com.holomentor.holomentor.dto.classTopic;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassTopicCreateDTO {
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "institute ID is required")
    private Long instituteId;
    @NotNull(message = "class ID is required")
    private Long classId;
}
