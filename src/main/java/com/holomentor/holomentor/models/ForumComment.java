package com.holomentor.holomentor.models;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="forum_comments")
public class ForumComment {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "question_id",nullable = false)
    private Long questionId;
    @Column(name = "user_id",nullable = false)
    private Long userId;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "comment_text",columnDefinition = "jsonb",nullable = false)
    private JsonNode comment;
   @Column(name = "created_at")
    private LocalDateTime createdAt;
}
