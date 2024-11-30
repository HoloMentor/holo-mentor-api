package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// create table quiz_answers (
//     id serial primary key,
//     user_id serial references users,
//     quiz_id serial references custom_quiz,
//     mcq_question_id serial references mcq_questions,
//     answer integer not null,
//   

@Data
@Entity
@Table(name = "quiz_answers")
public class QuizAnswer {

    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "mcq_question_id")
    private Long mcqQuestionId;

    @Column(name = "answer")
    private Integer answer;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

}