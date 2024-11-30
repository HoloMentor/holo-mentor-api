package com.holomentor.holomentor.repositories;


import com.holomentor.holomentor.models.QuizQuestion;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
// public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Id> {
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    Optional<QuizQuestion> findById(Long Id);

    @Transactional
    @Modifying
    @Query("UPDATE QuizQuestion q SET q.activation = :status WHERE q.id = :id")
    int updateActivationStatus(Long id, int status);

}
