package com.holomentor.holomentor.dto.classSubTopic;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassSubTopicUpdateDTO {
    @NotNull(message = "name is required")
    private String name;
}
