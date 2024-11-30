package com.holomentor.holomentor.services;

import com.holomentor.holomentor.models.CustomQuiz;
import com.holomentor.holomentor.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.holomentor.holomentor.utils.Response;

@Service
@Transactional
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public ResponseEntity<Object> getQuizzesByUserIdAndClassId(Long userId, Long classId) {
        List<CustomQuiz> quizzes = quizRepository.findByUserIdAndClassIdOrderByCreatedAtDesc(userId, classId);
        return ResponseEntity.ok(quizzes);
    }

    public ResponseEntity<Object> startQuizAttempt(Long quizId, Long userId) throws IOException {
        // Get quiz
        Optional<CustomQuiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent())
            return Response.generate("Quiz not found", HttpStatus.NOT_FOUND);

        CustomQuiz quizToUpdate = quiz.get();

        // Check if quiz is already started
        if (quizToUpdate.getStatus() == 1) {
            // if null, then the quiz is active
            if(quizToUpdate.getAttemptStartedAt() == null) {
                return Response.generate("Quiz already in progress", HttpStatus.OK);
            }

            // if the time is more than 30 minutes, then the quiz is expired
            LocalDateTime attemptStartedAt = quizToUpdate.getAttemptStartedAt();
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isAfter(attemptStartedAt.plusMinutes(30))) {
                quizToUpdate.setStatus(2); // 2 = Expired
                quizRepository.save(quizToUpdate);
                return Response.generate("Quiz attempt expired", HttpStatus.BAD_REQUEST);
            }

            return Response.generate("Quiz already in progress", HttpStatus.OK);
        }

        // Update quiz status and start time
        quizToUpdate.setStatus(1); // 1 = In Progress
        quizToUpdate.setAttemptStartedAt(LocalDateTime.now());

        quizRepository.save(quizToUpdate);
        return Response.generate("Quiz attempt started successfully", HttpStatus.OK);
    }

}