package com.holomentor.holomentor.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holomentor.holomentor.dto.forum.ForumQuestionCreateDTO;
import com.holomentor.holomentor.models.ForumQuestion;
import com.holomentor.holomentor.models.InstituteClass;
import com.holomentor.holomentor.repositories.ForumRepository;
import com.holomentor.holomentor.repositories.InstituteClassRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ForumService {
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private InstituteClassRepository instituteClassRepository;


    public ResponseEntity<Object> createMcq(ForumQuestionCreateDTO body) throws IOException {

        Optional<InstituteClass> instituteClassResult = instituteClassRepository.findById(body.getClassId());
        if (instituteClassResult.isEmpty()) {
            return Response.generate("class not found", HttpStatus.NOT_FOUND);
        }

        System.out.println("here is the body: check answer is comming or not: \n\n"+ body +"\n\n end of body \n\n");

        ForumQuestion forumQuestion = new ForumQuestion();
        forumQuestion.setClassId(body.getClassId());
        forumQuestion.setTopic(body.getTopic());
        forumQuestion.setSubTopic(body.getSubTopic());
        forumQuestion.setUserId(body.getUserId());
        forumQuestion.setMcqAnswer(body.getAnswers());
        forumQuestion.setVoteCount(0);
        forumQuestion.setQuestion(body.getQuestion());;
        forumQuestion.setAnswer("test");;

        System.out.println("here's creates forumquestion model: >>>: "+forumQuestion);


        forumRepository.save(forumQuestion);
        return Response.generate("question created successfully", HttpStatus.CREATED);
    }



    public ResponseEntity<Object> get(Long id) throws IOException{
        Optional<ForumQuestion> forumQuestion = forumRepository.findById(id);
        if(forumQuestion.isEmpty()){
            return Response.generate("forum question not found", HttpStatus.NOT_FOUND);
        }
        return Response.generate("forum question", HttpStatus.OK, forumQuestion.get());
    }

    public ResponseEntity<Object> getAll() throws IOException{
        List<ForumQuestion> forumQuestions = forumRepository.findAll();
        if(forumQuestions.isEmpty()){
            return Response.generate("No Questions to show", HttpStatus.NOT_FOUND);
        }
        else return Response.generate("forum Question List", HttpStatus.OK, forumQuestions);

    }


    public ResponseEntity<Object> delete(Long id) throws IOException{
        Optional<ForumQuestion> forumQuestion = forumRepository.findById(id);
        if(forumQuestion.isEmpty()){
            return Response.generate("forum question not found",HttpStatus.NOT_FOUND);
        }
        ForumQuestion forumQuestionDelete = forumQuestion.get();
        forumRepository.delete(forumQuestionDelete);
        return Response.generate("question deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity<Object> updateMcq(Long id,ForumQuestionCreateDTO body) throws IOException{
        Optional<ForumQuestion> forumQuestion = forumRepository.findById(id);
        if(forumQuestion.isEmpty()){
            return Response.generate("Forum question not found", HttpStatus.NOT_FOUND);
        }
        ForumQuestion forumQuestionUpdate = forumQuestion.get();
        forumQuestionUpdate.setClassId(body.getClassId());
        forumQuestionUpdate.setTopic(body.getTopic());
        forumQuestionUpdate.setSubTopic(body.getSubTopic());
        forumQuestionUpdate.setUserId(body.getUserId());
        forumQuestionUpdate.setMcqAnswer(body.getAnswers());
        forumQuestionUpdate.setVoteCount(body.getVoteCount());

        forumRepository.save(forumQuestionUpdate);
        return Response.generate("Mcq updated succesfully", HttpStatus.OK)
    }



}



