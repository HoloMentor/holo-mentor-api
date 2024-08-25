package com.holomentor.holomentor.dto.forum;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForumQuestionCreateDTO {
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "class ID is required")
    private Long classId;
    @NotNull(message = "question is required")
    private String question;
    @NotNull(message = "user ID is required")
    private Long userId;
}
