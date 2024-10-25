package com.holomentor.holomentor.dto.classTopic;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassTopicUpdateDTO {
    @NotNull(message = "name is required")
    private String name;
}
