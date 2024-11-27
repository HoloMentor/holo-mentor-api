package com.holomentor.holomentor.repositories;


import com.holomentor.holomentor.models.QuizQuestion;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Id> {
    Optional<QuizQuestion> findById(Long Id);
}
