package com.holomentor.holomentor.services;

import com.holomentor.holomentor.models.CustomQuiz;
import com.holomentor.holomentor.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public ResponseEntity<Object> getQuizzesByUserIdAndClassId(Long userId, Long classId) {
        List<CustomQuiz> quizzes = quizRepository.findByUserIdAndClassIdOrderByCreatedAtDesc(userId, classId);
        return ResponseEntity.ok(quizzes);
    }


}