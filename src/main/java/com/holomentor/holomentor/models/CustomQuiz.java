package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

import com.fasterxml.jackson.databind.JsonNode;
// import com.holomentor.holomentor.controllers.Quiz;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

//create table custom_quiz (
//        id serial primary key,
//        quiz_name varchar,
//        class_id serial references institute_classes,
//        user_id serial references users,
//        mcq_question_ids jsonb not null,
//        status int not null default 0,
//        created_at timestamp default current_timestamp,
//);

@Data
@Entity
@Table(name = "custom_quiz")
public class CustomQuiz {

    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quiz_name")
    private String quizName;
    @Column(name = "class_id")
    private Long classId;
    @Column(name = "user_id")
    private Long userId;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "mcq_question_ids", columnDefinition = "jsonb")
    private JsonNode mcqQuestionIds;
    private int status;
    @Column(name = "created_at")
    private String createdAt;

}