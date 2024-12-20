package com.holomentor.holomentor.dto.forum;

import com.fasterxml.jackson.databind.JsonNode;
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
    private JsonNode question;
    @NotNull(message = "user ID is required")
    private Long userId;
    @NotNull(message = "Topic is Required")
    private String topic;
    @NotNull(message = "sub topic is required")
    private String subTopic;
    private JsonNode answers;
    private int voteCount;
    private String answer;
}
