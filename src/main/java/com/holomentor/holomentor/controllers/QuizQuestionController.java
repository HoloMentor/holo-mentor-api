package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.quizzes.QuizQuestionCreateDTO;
import com.holomentor.holomentor.models.QuizQuestion;
import com.holomentor.holomentor.services.QuizeQuestionService;
import com.holomentor.holomentor.utils.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("question")
public class QuizQuestionController {

    @Autowired
    private QuizeQuestionService quizeQuestionService;
    @PostMapping("/add")
    public ResponseEntity<Object> createQuestionForQuiz(@Valid @RequestBody QuizQuestionCreateDTO body) throws IOException{
        System.out.println(body);
        return quizeQuestionService.createQuestionForQuize(body);
    }

    @GetMapping("/view")
    public ResponseEntity<Object> getAll() throws IOException{
        return quizeQuestionService.getAll();
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Object> deactivateQuestion(@PathVariable @Valid Long id) throws IOException{
        return quizeQuestionService.deactivateQuestion(id);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Object> activateQuestion(@PathVariable @Valid Long id) throws IOException{
        return quizeQuestionService.activateQuestion(id);
    }
}
