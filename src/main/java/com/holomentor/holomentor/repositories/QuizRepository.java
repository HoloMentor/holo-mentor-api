package com.holomentor.holomentor.repositories;


//import com.holomentor.holomentor.controllers.Quiz;
import com.holomentor.holomentor.models.CustomQuiz;
import com.holomentor.holomentor.services.QuizResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
// import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<CustomQuiz, Long> {
    List<CustomQuiz> findByUserIdAndClassIdOrderByCreatedAtDesc(Long userId, Long classId);

//    QuizResult findQuizResult(Long quizId, Long userId);
//
//    Map<String, Object> getQuizStats(Long quizId);

//    Quiz save(Quiz quiz);

//    Page<Quiz> findByClassId(Long classId, PageRequest pageable);
}