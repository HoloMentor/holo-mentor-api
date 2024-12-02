package com.holomentor.holomentor.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.holomentor.holomentor.models.CustomQuiz;
import com.holomentor.holomentor.repositories.*;
import com.holomentor.holomentor.utils.Response;
import com.holomentor.holomentor.models.QuizAnswer;
import com.holomentor.holomentor.models.QuizQuestion;
import com.holomentor.holomentor.repositories.QuizRepository;
import com.holomentor.holomentor.repositories.QuizQuestionRepository;
import com.holomentor.holomentor.repositories.QuizAnswerRepository;
// import com.holomentor.holomentor.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.holomentor.holomentor.utils.Response;

@Service
@Transactional
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;

    public ResponseEntity<Object> getQuizzesByUserIdAndClassId(Long userId, Long classId) {
        List<CustomQuiz> quizzes = quizRepository.findByUserIdAndClassIdOrderByCreatedAtDesc(userId, classId);
        return ResponseEntity.ok(quizzes);
    }

    public ResponseEntity<Object> getQuizStats(Long user_id , Long institute_id) {
        long quizCount = quizRepository.countQuizzes(user_id , institute_id);
        return Response.generate("quiz statistics are found", HttpStatus.OK, quizCount);
    }

    public ResponseEntity<Object> startQuizAttempt(Long quizId, Long userId) throws IOException {
        // Get quiz
        Optional<CustomQuiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent())
            return Response.generate("Quiz not found", HttpStatus.NOT_FOUND);

        CustomQuiz quizToUpdate = quiz.get();

        // Check if quiz is already started
        if (quizToUpdate.getStatus() == 1 || quizToUpdate.getStatus() == 2) {
            // if null, then the quiz is active
            if (quizToUpdate.getAttemptStartedAt() == null) {
                return Response.generate("Quiz already in progress", HttpStatus.OK);
            }

            // if the time is more than 30 minutes, then the quiz is expired
            LocalDateTime attemptStartedAt = quizToUpdate.getAttemptStartedAt();
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isAfter(attemptStartedAt.plusMinutes(30))) {
                quizToUpdate.setStatus(2); // 2 = Expired
                quizRepository.save(quizToUpdate);
                return Response.generate("Quiz attempt expired", HttpStatus.OK, attemptStartedAt);
            }

            return Response.generate("Quiz already in progress", HttpStatus.OK, attemptStartedAt);
            // return Response.generate("Quiz already in progress", HttpStatus.OK);
        }

        // Update quiz status and start time
        quizToUpdate.setStatus(1); // 1 = In Progress
        quizToUpdate.setAttemptStartedAt(LocalDateTime.now());
        quizRepository.save(quizToUpdate);

        return Response.generate("Quiz attempt started successfully", HttpStatus.OK,
                quizToUpdate.getAttemptStartedAt());
    }

    @Transactional
    public ResponseEntity<Object> submitQuestion(Long quizId, Long questionId, Long userId, Map<String, Object> body)
            throws IOException {
        // check quiz
        Optional<CustomQuiz> quiz = quizRepository.findById(quizId);
        if (!quiz.isPresent()) {
            return Response.generate("Quiz not found", HttpStatus.NOT_FOUND);
        }

        // CustomQuiz currentQuiz = quiz.get();
        // if (currentQuiz.getStatus() != 1) {
        // return Response.generate("Quiz is not active", HttpStatus.BAD_REQUEST);
        // }

        // check if q exists
        Optional<QuizQuestion> question = quizQuestionRepository.findById(questionId);
        if (!question.isPresent()) {
            return Response.generate("Question not found", HttpStatus.NOT_FOUND);
        }

        Integer answer;
        try {
            answer = (Integer) body.get("answer");
            if (answer == null)
                return Response.generate("Answer is required", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return Response.generate("Invalid answer format", HttpStatus.BAD_REQUEST);
        }

        // check if answer already exists
        Optional<QuizAnswer> existingAnswer = quizAnswerRepository.findByUserIdAndQuizIdAndMcqQuestionId(userId, quizId,
                questionId);
        if (existingAnswer.isPresent()) {
            // update existing answer
            QuizAnswer answerToUpdate = existingAnswer.get();
            answerToUpdate.setAnswer(answer);
            quizAnswerRepository.save(answerToUpdate);
            return Response.generate("Answer updated successfully", HttpStatus.OK);
        }

        // create new answer
        QuizAnswer newAnswer = new QuizAnswer();
        newAnswer.setUserId(userId);
        newAnswer.setQuizId(quizId);
        newAnswer.setMcqQuestionId(questionId);
        newAnswer.setAnswer(answer);
        newAnswer.setCreatedAt(LocalDateTime.now());
        quizAnswerRepository.save(newAnswer);

        return Response.generate("Answer submitted successfully", HttpStatus.OK);
    }

    public ResponseEntity<Object> getQuizWithAnswers(Long quizId, Long userId) {
        Optional<CustomQuiz> quiz = quizRepository.findById(quizId);
        if (!quiz.isPresent())
            return Response.generate("Quiz not found", HttpStatus.NOT_FOUND);

        CustomQuiz currentQuiz = quiz.get();
        JsonNode questionIds = currentQuiz.getMcqQuestionIds();

        if (questionIds == null || !questionIds.isArray())
            return Response.generate("No questions found in quiz", HttpStatus.NOT_FOUND);

        List<Map<String, Object>> questionsWithAnswers = new ArrayList<>();

        for (JsonNode questionIdNode : questionIds) {
            Long questionId = questionIdNode.asLong();

            Optional<QuizQuestion> questionOpt = quizQuestionRepository.findById(questionId);
            if (!questionOpt.isPresent())
                continue;

            QuizQuestion question = questionOpt.get();

            // Get students answer
            Optional<QuizAnswer> answerOpt = quizAnswerRepository
                    .findByUserIdAndQuizIdAndMcqQuestionId(userId, quizId, questionId);

            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question_id", question.getId());
            questionData.put("question", question.getQuestion());
            questionData.put("topic", question.getTopic());
            questionData.put("sub_topic", question.getSubTopic());
            questionData.put("mcq_answers", question.getMcqAnswer());
            questionData.put("correct_answer", question.getAnswer());
            questionData.put("student_answer", answerOpt.isPresent() ? answerOpt.get().getAnswer() : null);

            questionsWithAnswers.add(questionData);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("quiz_id", quizId);
        response.put("quiz_name", currentQuiz.getQuizName());
        response.put("status", currentQuiz.getStatus());
        response.put("attempt_started_at", currentQuiz.getAttemptStartedAt());
        response.put("questions", questionsWithAnswers);

        return Response.generate("Quiz data retrieved successfully", HttpStatus.OK, response);
    }

    // reattemptQuiz
    public ResponseEntity<Object> reattemptQuiz(Long quizId, Long userId) {
        Optional<CustomQuiz> quiz = quizRepository.findById(quizId);
        if (!quiz.isPresent())
            return Response.generate("Quiz not found", HttpStatus.NOT_FOUND);

        CustomQuiz currentQuiz = quiz.get();
        currentQuiz.setStatus(0);

        // set attemptStartedAt to now
        currentQuiz.setAttemptStartedAt(LocalDateTime.now());
        quizRepository.save(currentQuiz);

        return Response.generate("Quiz status reset successfully", HttpStatus.OK, currentQuiz.getAttemptStartedAt());
    }
}