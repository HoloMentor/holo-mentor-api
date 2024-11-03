package com.holomentor.holomentor.dto.classSubTopic;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassSubTopicCreateDTO {
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "institute ID is required")
    private Long instituteId;
    @NotNull(message = "topic ID is required")
    private Long topicId;
    @NotNull(message = "class ID is required")
    private Long classId;
}
