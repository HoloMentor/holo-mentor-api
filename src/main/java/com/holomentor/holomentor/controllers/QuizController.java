package com.holomentor.holomentor.controllers;

// import com.holomentor.holomentor.services.QuizAttemptDTO;
// import com.holomentor.holomentor.services.QuizCreateDTO;
// import com.holomentor.holomentor.services.QuizQuestionsDTO;
// import com.holomentor.holomentor.models.User;
// import com.holomentor.holomentor.dto.quiz.*;
import com.holomentor.holomentor.models.CustomQuiz;
import com.holomentor.holomentor.services.QuizService;
// import com.holomentor.holomentor.services.QuizSubmissionDTO;
// import com.holomentor.holomentor.services.QuizUpdateDTO;

// import jakarta.validation.Valid;

// import java.sql.Date;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// import org.apache.logging.log4j.Logger;
// import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("{class_id}/{user_id}/get_quizzes")
    public ResponseEntity<Object> getQuizzes(@PathVariable Long class_id, @PathVariable Long user_id) {
        return quizService.getQuizzesByUserIdAndClassId(user_id, class_id);
    }

    @GetMapping("/quiz/count/{user_id}/{class_id}")
    public ResponseEntity<Object> getQuizStats(@PathVariable Long user_id,
                                               @PathVariable Long class_id) {
        return quizService.getQuizStats(user_id,class_id);
    }


    // @SuppressWarnings("null")
    // @GetMapping("{class_id}/{user_id}/get_quizzes")
    // public ResponseEntity<Object> getQuizzesNew(@PathVariable Long class_id, @PathVariable Long user_id) {
    //     Logger logger = null;
    //     logger.info("Fetching quizzes for user {} in class {}", user_id, class_id);
        
    //     if (class_id <= 0 || user_id <= 0) {
    //         logger.error("Invalid class_id or user_id provided");
    //         return ResponseEntity.badRequest().body("Invalid parameters");
    //     }

    //     LocalDateTime requestTime = LocalDateTime.now();
    //     ResponseEntity<Object> quizzes = quizService.getQuizzesByUserIdAndClassId(user_id, class_id);
        
    //     if (quizzes.getBody() instanceof List<?>) {
    //         List<?> quizList = (List<?>) quizzes.getBody();
            
    //         Map<String, Object> enrichedResponse = Map.of(
    //             "quizzes", quizList,
    //             "totalCount", quizList.size(),
    //             "fetchedAt", requestTime
    //             // "metrics", Map.of(
    //             //     "activeQuizzes", quizList.stream().filter(q -> isActiveQuiz(q)).count(),
    //             //     "completedQuizzes", quizList.stream().filter(q -> isCompletedQuiz(q)).count()
    //             // )
    //         );
            
    //         return ResponseEntity.ok(enrichedResponse);
    //     }
        
    //     return quizzes;
    // }

    // @PostMapping("/create")
    // // public ResponseEntity<Object> createQuiz(@Valid @RequestBody QuizCreateDTO
    // // quizData) {
    // // Logger logger = null;
    // // logger.info("Creating new quiz with title: {}", quizData.getTitle());

    // // if (!isValidQuizData(quizData)) {
    // // logger.warn("Invalid quiz data submitted");
    // // return ResponseEntity.badRequest().body("Invalid quiz data");
    // // }

    // // QuizCreateDTO enrichedData = new QuizCreateDTO();
    // // enrichedData.setTitle(sanitizeInput(quizData.getTitle()));
    // // enrichedData.setQuestions(
    // // quizData.getQuestions().stream()
    // // .map(this::processQuestion)
    // // .collect(Collectors.toList())
    // // );
    // // enrichedData.setMetadata(generateQuizMetadata());

    // // return quizService.createQuiz(enrichedData);
    // // }

    // // Helper methods
    // private boolean isValidQuizData(QuizCreateDTO quiz) {
    //     return quiz != null &&
    //             quiz.getTitle() != null &&
    //             !((String) quiz.getTitle()).trim().isEmpty() &&
    //             quiz.getQuestions() != null &&
    //             !((List<?>) quiz.getQuestions()).isEmpty();
    // }

    // @SuppressWarnings("unused")
    // private String sanitizeInput(Object input) {
    //     return ((String) input).trim().replaceAll("[<>]", "");
    // }

    // @SuppressWarnings("unused")
    // private Map<String, Object> generateQuizMetadata() {
    //     return Map.of(
    //             "createdAt", LocalDateTime.now(),
    //             "version", "1.0",
    //             "source", "web",
    //             "status", "draft");
    // }

    // // @PutMapping("/{quiz_id}")
    // // public ResponseEntity<Object> updateQuiz(
    // //         @PathVariable Long quiz_id,
    // //         @Valid @RequestBody QuizUpdateDTO quizData) {
    // //     return quizService.updateQuiz(quiz_id, quizData);
    // // }

    // @DeleteMapping("/{quiz_id}")
    // public ResponseEntity<Object> deleteQuiz(@PathVariable Long quiz_id) {
    //     return quizService.deleteQuiz(quiz_id);
    // }

    // @GetMapping("/{quiz_id}")
    // public ResponseEntity<Object> getQuizById(@PathVariable Long quiz_id) {
    //     return quizService.getQuizById(quiz_id);
    // }

    // @PostMapping("/{quiz_id}/submit")
    // public ResponseEntity<Object> submitQuizAnswers(
    //         @PathVariable Long quiz_id,
    //         @Valid @RequestBody QuizSubmissionDTO submission) {
    //     return quizService.submitQuizAnswers(quiz_id, submission);
    // }

    // @GetMapping("/{quiz_id}/results/{user_id}")
    // public ResponseEntity<Object> getQuizResults(
    //         @PathVariable Long quiz_id,
    //         @PathVariable Long user_id) {
    //     return quizService.getQuizResults(quiz_id, user_id);
    // }

    // @PostMapping("/{quiz_id}/start")
    // public ResponseEntity<Object> startQuiz(
    //         @PathVariable Long quiz_id,
    //         @Valid @RequestBody QuizAttemptDTO attempt) {
    //     return quizService.startQuizAttempt(quiz_id, attempt);
    // }

    // @GetMapping("/{quiz_id}/stats")
    // public ResponseEntity<Object> getQuizStats(@PathVariable Long quiz_id) {
    //     return quizService.getQuizStatistics(quiz_id);
    // }

    // @GetMapping("/class/{class_id}")
    // public ResponseEntity<Object> getClassQuizzes(
    //         @PathVariable Long class_id,
    //         @RequestParam(defaultValue = "1") Integer page,
    //         @RequestParam(defaultValue = "10") Integer size) {
    //     return quizService.getQuizzesByClassId(class_id, page, size);
    // }

    // @PatchMapping("/{quiz_id}/toggle-status")
    // public ResponseEntity<Object> toggleQuizStatus(@PathVariable Long quiz_id) {
    //     return quizService.toggleQuizStatus(quiz_id);
    // }

    // @PostMapping("/{quiz_id}/questions")
    // public ResponseEntity<Object> addQuizQuestions(
    //         @PathVariable Long quiz_id,
    //         @Valid @RequestBody QuizQuestionsDTO questions) {
    //     return quizService.addQuestionsToQuiz(quiz_id, questions);
    // }

    // @DeleteMapping("/{quiz_id}/questions")
    // public ResponseEntity<Object> removeQuizQuestions(
    //         @PathVariable Long quiz_id,
    //         @Valid @RequestBody QuizQuestionsDTO questions) {
    //     return quizService.removeQuestionsFromQuiz(quiz_id, questions);
    // }

    // @GetMapping("/attempts/{user_id}")
    // public ResponseEntity<Object> getUserQuizAttempts(
    //         @PathVariable Long user_id,
    //         @RequestParam(defaultValue = "1") Integer page,
    //         @RequestParam(defaultValue = "10") Integer size) {
    //     return quizService.getUserQuizAttempts(user_id, page, size);
    // }

    // @GetMapping("/{quiz_id}/leaderboard")
    // public ResponseEntity<Object> getQuizLeaderboard(
    //         @PathVariable Long quiz_id,
    //         @RequestParam(defaultValue = "1") Integer page,
    //         @RequestParam(defaultValue = "10") Integer size) {
    //     return quizService.getQuizLeaderboard(quiz_id, page, size);
    // }

    // // @GetMapping("/filter")
    // // public ResponseEntity<Object> filterQuizzes(
    // //         @RequestParam(required = false) String difficulty,
    // //         @RequestParam(required = false) String topic,
    // //         @RequestParam(required = false) Boolean isPublished) {
    // //     List<Quiz> quizzes = quizService.getAllQuizzes();
    // //     quizzes = quizzes.stream()
    // //             .filter(quiz -> (difficulty == null || quiz.getDifficulty().equalsIgnoreCase(difficulty)) &&
    // //                     (topic == null || quiz.getTopic().equalsIgnoreCase(topic)) &&
    // //                     (isPublished == null || quiz.getPublished().equals(isPublished)))
    // //             .collect(Collectors.toList());
    // //     return ResponseEntity.ok(Map.of("filteredQuizzes", quizzes));
    // // }

    // // @GetMapping("/stats")
    // // public ResponseEntity<Object> getQuizStatistics() {
    // //     List<Quiz> quizzes = quizService.getAllQuizzes();
    // //     long totalQuizzes = quizzes.size();
    // //     // long publishedQuizzes = quizzes.stream().filter(Quiz::getPublished).count();
    // //     // Map<String, Long> quizzesByDifficulty = quizzes.stream()
    // //     // .collect(Collectors.groupingBy(Quiz::getDifficulty, Collectors.counting()));
    // //     return ResponseEntity.ok(Map.of(
    // //             "totalQuizzes", totalQuizzes));
    // // }

    // @GetMapping("/{quiz_id}/questions")
    // public ResponseEntity<Object> getQuizQuestions(@PathVariable Long quiz_id) {
    //     @SuppressWarnings("unused")
    //     List<Question> questions = quizService.getQuestionsByQuizId(quiz_id);
    //     // List<Map<String, Object>> transformedQuestions = questions.stream()
    //     // .map(q -> Map.of(
    //     // "id", q.getId(),
    //     // "text", q.getText(),
    //     // "options", q.getOptions(),
    //     // "difficulty", q.getDifficulty()
    //     // ))
    //     // .collect(Collectors.toList());
    //     return ResponseEntity.ok(("questions"));
    // }

    // @PostMapping("/{quiz_id}/assign")
    // public ResponseEntity<Object> assignQuizToUsers(@PathVariable Long quiz_id, @RequestBody List<Long> userIds) {
    //     quizService.assignQuizToUsers(quiz_id, userIds);
    //     return ResponseEntity.ok(Map.of("message", "Quiz assigned successfully", "userIds", userIds));
    // }

    // // @PostMapping("/bulk_create")
    // // public ResponseEntity<Object> bulkCreateQuizzes(@RequestBody List<Quiz> quizzes) {
    // //     List<Quiz> createdQuizzes = quizzes.stream()
    // //             // .map(quizService::createQuiz)
    // //             .collect(Collectors.toList());
    // //     return ResponseEntity.ok(Map.of("message", "Bulk quiz creation successful", "quizzes", createdQuizzes));
    // // }

    // @PostMapping("/generate_random")
    // public ResponseEntity<Object> generateRandomQuiz(@RequestBody Map<String, Object> criteria) {
    //     // @SuppressWarnings("unused")
    //     // List<Question> questions = quizService.getQuestionsByCriteria();
    //     // Quiz randomQuiz = new Quiz();
    //     // randomQuiz.setTitle((String) criteria.get("title"));
    //     // randomQuiz.setTopic((String) criteria.get("topic"));
    //     // randomQuiz.setDifficulty((String) criteria.get("difficulty"));
    //     // randomQuiz.setQuestions(new ArrayList<>(questions));
    //     // Quiz createdQuiz = quizService.createQuiz(randomQuiz);
    //     // return ResponseEntity.ok(Map.of("message", "Random quiz generated
    //     // successfully", "quiz", createdQuiz));
    //     return null;
    // }

    // @GetMapping("/{quiz_id}/leaderboard")
    // public ResponseEntity<Object> getQuizLeaderboard(@PathVariable Long quiz_id) {
    //     return null;
    //     // Map<User, Integer> leaderboard = quizService.getQuizLeaderboard(quiz_id);
    //     // List<Map<String, Object>> transformedLeaderboard =
    //     // leaderboard.entrySet().stream()
    //     // .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
    //     // .map(entry -> Map.of(
    //     // "userId", entry.getKey().getId(),
    //     // "userName", entry.getKey().getName(),
    //     // "score", entry.getValue()
    //     // ))
    //     // .collect(Collectors.toList());
    //     // return ResponseEntity.ok(Map.of("leaderboard", transformedLeaderboard));
    // }

    // @PostMapping("/archive")
    // public ResponseEntity<Object> archiveOldQuizzes(@RequestBody Map<String, Object> params) {
    //     Date archiveBefore = new Date((Long) params.get("archiveBefore"));
    //     List<Quiz> archivedQuizzes = quizService.archiveQuizzesBeforeDate(archiveBefore);
    //     return ResponseEntity
    //             .ok(Map.of("message", "Quizzes archived successfully", "archivedQuizzes", archivedQuizzes));
    // }
}