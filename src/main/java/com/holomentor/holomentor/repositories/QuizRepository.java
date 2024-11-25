package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.CustomQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// import java.util.M

@Repository
public interface QuizRepository extends JpaRepository<CustomQuiz, Long> {
    List<CustomQuiz> findByUserIdAndClassIdOrderByCreatedAtDesc(Long userId, Long classId);
}