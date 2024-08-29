package com.holomentor.holomentor.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "forum_questions")
public class ForumQuestion {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "class_id", nullable = false)
    private Long classId;
    @Column(name = "vote_count", nullable = false)
    private int voteCount;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String question;
    @Column(nullable = true)
    private String answer;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionVotes> votes;
}
