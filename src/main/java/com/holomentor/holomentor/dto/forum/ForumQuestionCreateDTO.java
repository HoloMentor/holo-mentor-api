package com.holomentor.holomentor.dto.forum;

import io.swagger.v3.core.util.Json;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class ForumQuestionCreateDTO {
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "class ID is required")
    private Long classId;
    @NotNull(message = "question is required")
    private Object question;
    @NotNull(message = "user ID is required")
    private Long userId;
    @NotNull(message = "Topic is Required")
    private String topic;
    @NotNull(message = "sub topic is required")
    private String subTopic;
    private List<String> answersMcq;
    private String answer;
}
