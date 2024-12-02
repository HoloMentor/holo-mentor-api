package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "question_votes")
public class QuestionVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long questionId;
    @Column(name = "user_id" , nullable = false)
    private Long userId;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "vote_type" , nullable = false)
    private VoteTypes voteType;

    @PrePersist
    @PreUpdate
    private void validateVoteType(){
        if(voteType == null){
            voteType = VoteTypes.NONE;
        }

    }

    public enum VoteTypes{
        NONE,
        VOTE_UP,
        VOTE_DOWN
    }
}
