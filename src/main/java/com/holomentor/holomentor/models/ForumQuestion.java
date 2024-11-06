package com.holomentor.holomentor.models;


import jakarta.persistence.*;
import lombok.Data;


import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;


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
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "question", columnDefinition = "jsonb", nullable = false)
    private JsonNode question;
    @Column(name = "essay_answer")
    private String essayAnswer;
    @Column(name = "answer")
    private String answer;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "mcq_answers", columnDefinition = "jsonb")
    private JsonNode mcqAnswer;
}
