package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.quizzes.QuizQuestionCreateDTO;
import com.holomentor.holomentor.models.InstituteClass;
import com.holomentor.holomentor.models.QuizQuestion;
import com.holomentor.holomentor.repositories.InstituteClassRepository;
import com.holomentor.holomentor.repositories.QuizQuestionRepository;
import com.holomentor.holomentor.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class QuizeQuestionService {
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;


    @Autowired
    private InstituteClassRepository instituteClassRepository;
    public ResponseEntity<Object> createQuestionForQuize(QuizQuestionCreateDTO body) throws IOException{

        System.out.println("second");
        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setClassId(body.getClassId());
        System.out.println(body.getClassId());
        quizQuestion.setQuestion(body.getQuestion());
        System.out.println(body.getQuestion());
        quizQuestion.setUserId(body.getUserId());
        System.out.println(body.getUserId());
        quizQuestion.setTopic(body.getTopic());
        System.out.println(body.getTopic());
        quizQuestion.setSubTopic(body.getSubTopic());
        System.out.println(body.getSubTopic());
        quizQuestion.setMcqAnswer(body.getAnswers());
        System.out.println(body.getAnswers());
        quizQuestion.setAnswer(body.getCorrectAnswer());
        System.out.println(body.getCorrectAnswer());
        System.out.println("third");

        quizQuestionRepository.save(quizQuestion);
        System.out.println("fourth");
        return Response.generate("question created successfully", HttpStatus.CREATED);

    }

    public ResponseEntity<Object> getAll() throws IOException{
        List<QuizQuestion> quizQuestionList = quizQuestionRepository.findAll();
        if(quizQuestionList.isEmpty()){
            return Response.generate("Nothing to show Quiz Bank is Empty",HttpStatus.NO_CONTENT);
        }
        else{
            return Response.generate("Operation Completed Successfully",HttpStatus.OK,quizQuestionList);
        }
    }

    public ResponseEntity deactivateQuestion(Long id) throws IOException{
        Optional<QuizQuestion> quizQuestion = quizQuestionRepository.findById(id);
        if(quizQuestion.isEmpty()){
            return Response.generate("Question Not found", HttpStatus.NOT_FOUND);
        }

        quizQuestionRepository.updateActivationStatus(id,1);
        return  Response.generate("Question deactivated successfully", HttpStatus.OK);
    }

    public ResponseEntity activateQuestion(Long id) throws IOException{
        Optional<QuizQuestion> quizQuestion = quizQuestionRepository.findById(id);
        if(quizQuestion.isEmpty()){
            return Response.generate("Question Not found", HttpStatus.NOT_FOUND);
        }

        quizQuestionRepository.updateActivationStatus(id,0);
        return  Response.generate("Question activated successfully", HttpStatus.OK);
    }
}
