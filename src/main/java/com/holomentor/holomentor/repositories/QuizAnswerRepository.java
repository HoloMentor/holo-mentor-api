package com.holomentor.holomentor.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.holomentor.holomentor.models.QuizAnswer;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
    Optional<QuizAnswer> findByQuizIdAndMcqQuestionIdAndUserId(Long quizId, Long questionId, Long userId);

    Optional<QuizAnswer> findByQuizIdAndUserId(Long quizId, Long userId);

    Optional<QuizAnswer> findByQuizIdAndMcqQuestionId(Long quizId, Long questionId);

    Optional<QuizAnswer> findByUserIdAndQuizIdAndMcqQuestionId(Long userId, Long quizId, Long questionId);
}

