package com.holomentor.holomentor.models;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "mcq_questions")
public class QuizQuestion {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "class_id", nullable = false)
    private Long classId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "topic", nullable = false)
    private String topic;
    @Column(name = "sub_topic",nullable = false)
    private String subTopic;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "question", columnDefinition = "jsonb", nullable = false)
    private JsonNode question;
    @Column(name = "answer")
    private Long answer;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "mcq_answers", columnDefinition = "jsonb")
    private JsonNode mcqAnswer;
}
