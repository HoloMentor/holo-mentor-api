package com.holomentor.holomentor.dto.classSubTopic;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassSubTopicDoneStateUpdateDTO {
    @NotNull(message = "sub topic done state is required")
    private Boolean isDone;
}
