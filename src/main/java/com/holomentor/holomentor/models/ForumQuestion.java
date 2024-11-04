package com.holomentor.holomentor.models;


import jakarta.persistence.*;
import lombok.Data;


import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.Type;


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
    @Column(name = "topic", nullable = false)
    private String topic;
    @Column(name = "sub_topic",nullable = false)
    private String subTopic;
    @Column(name = "question",nullable = false,columnDefinition = "json")
    private Object question;
    @Column(name = "essay_answer")
    private String essayAnswer;
    @ElementCollection
    @CollectionTable(name = "mcq_answers", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "mcq_answer")
    private List<String> mcqAnswer;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionVotes> votes;
}
