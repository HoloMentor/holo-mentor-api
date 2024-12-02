package com.holomentor.holomentor.dto.forumComment;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForumCommentDTO {

    @NotNull(message = "question id is required")
    private Long questionId;
    @NotNull(message = "user Id is required")
    private Long userId;
    @NotNull(message = "Comment Text is required")
    private JsonNode commentText;
}
