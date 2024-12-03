package com.holomentor.holomentor.dto.forumComment;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForumCommentWithUserDTO {
    private Long questionId;
    private Long userId;
    private JsonNode comment;
    private LocalDateTime createdAt;
    private String firstName;
    private String lastName;

    // Updated constructor to match the fields
    public ForumCommentWithUserDTO(Long questionId, Long userId, JsonNode comment, LocalDateTime createdAt, String firstName, String lastName) {
        this.questionId = questionId;
        this.userId = userId;
        this.comment = comment;
        this.createdAt = createdAt;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
