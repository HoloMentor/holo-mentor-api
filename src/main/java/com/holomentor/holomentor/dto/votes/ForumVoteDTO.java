package com.holomentor.holomentor.dto.votes;
import com.holomentor.holomentor.models.QuestionVotes;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForumVoteDTO {
    @NotNull(message = "qid is required")
    private Long questionId;
    @NotNull(message = "userId is requires")
    private Long userId;
    @NotNull(message = "vote Type is required")
    private QuestionVotes.VoteTypes voteType;
}
