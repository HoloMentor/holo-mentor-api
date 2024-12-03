package com.holomentor.holomentor.controllers;

// import com.holomentor.holomentor.models.CustomQuiz;
// import com.holomentor.holomentor.repositories.QuizAnswerRepository;
import com.holomentor.holomentor.models.QuizQuestion;
import com.holomentor.holomentor.services.QuizService;
import com.holomentor.holomentor.utils.Response;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private com.holomentor.holomentor.repositories.QuizQuestionRepository quizQuestionRepository;

    // @Autowired
    // private QuizAnswerRepository quizAnswerRepository;

    @GetMapping("{class_id}/{user_id}/get_quizzes")
    public ResponseEntity<Object> getQuizzes(@PathVariable Long class_id, @PathVariable Long user_id) {
        return quizService.getQuizzesByUserIdAndClassId(user_id, class_id);
    }

    @GetMapping("/count/{user_id}/{class_id}")
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
    @PostMapping("/{quiz_id}/{user_id}/start-attempt")
    public ResponseEntity<Object> startQuizAttempt(@PathVariable Long quiz_id, @PathVariable Long user_id)
            throws IOException {
        return quizService.startQuizAttempt(quiz_id, user_id);
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Object> getQuestionById(@PathVariable @Valid Long id) throws IOException {
        Optional<QuizQuestion> question = quizQuestionRepository.findById(id);
        if (question.isEmpty())
            return Response.generate("Question not found", HttpStatus.NOT_FOUND);

        return Response.generate("Question found", HttpStatus.OK, question.get());
    }

    // /question/${quiz_id}/${questionId}/${userId}/submit
    @PostMapping("/question/{quiz_id}/{question_id}/{user_id}/submit")
    public ResponseEntity<Object> submitQuestion(@PathVariable Long quiz_id, @PathVariable Long question_id,
            @PathVariable Long user_id, @RequestBody Map<String, Object> body) throws IOException {
        return quizService.submitQuestion(quiz_id, question_id, user_id, body);
    }

    @GetMapping("/{quiz_id}/{user_id}/answers")
    public ResponseEntity<Object> getQuizWithAnswers(@PathVariable Long quiz_id, @PathVariable Long user_id) {
        return quizService.getQuizWithAnswers(quiz_id, user_id);
    }

    // reattempt
    @GetMapping("/{quiz_id}/{user_id}/reattempt")
    public ResponseEntity<Object> reattemptQuiz(@PathVariable Long quiz_id, @PathVariable Long user_id) {
        return quizService.reattemptQuiz(quiz_id, user_id);
    }

    // create quiz using ML model
    // http://localhost:8082/generate_quiz/${class_id}/${user_id}
    @GetMapping("/generate-quiz/{class_id}/{user_id}")
    public ResponseEntity<Object> generateQuiz(@PathVariable Long class_id, @PathVariable Long user_id) {
        return quizService.generateQuiz(class_id, user_id);
    }

}