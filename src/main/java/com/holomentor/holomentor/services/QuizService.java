package com.holomentor.holomentor.services;

import com.holomentor.holomentor.models.CustomQuiz;
import com.holomentor.holomentor.repositories.*;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<Object> getQuizStats(Long user_id , Long institute_id) {
        long quizCount = quizRepository.countQuizzes(user_id , institute_id);
        return Response.generate("quiz statistics are found", HttpStatus.OK, quizCount);
    }


}