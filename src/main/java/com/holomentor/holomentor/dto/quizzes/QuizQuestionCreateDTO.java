package com.holomentor.holomentor.dto.quizzes;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizQuestionCreateDTO {

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
    @NotNull(message = "multipe choices are requires")
    private JsonNode answers;
    @NotNull(message = "correct answer is required")
    private Long correctAnswer;
}
