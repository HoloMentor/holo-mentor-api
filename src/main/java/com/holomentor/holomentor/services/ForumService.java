package com.holomentor.holomentor.services;

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

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

@Service
public class ForumService {
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private InstituteClassRepository instituteClassRepository;


    public ResponseEntity<Object> create(ForumQuestionCreateDTO body) throws IOException {

        Optional<InstituteClass> instituteClassResult = instituteClassRepository.findById(body.getClassId());
        if (instituteClassResult.isEmpty()) {
            return Response.generate("class not found", HttpStatus.NOT_FOUND);
        }


        ForumQuestion forumQuestion = new ForumQuestion();
        forumQuestion.setQuestion(body.getQuestion());
        forumQuestion.setUserId(body.getUserId());
        forumQuestion.setVoteCount(0);

        forumRepository.save(forumQuestion);
        return Response.generate("question created successfully", HttpStatus.CREATED);
    }


    public ResponseEntity<Object> update(Long id,ForumQuestionCreateDTO body) throws IOException{
        Optional<ForumQuestion> forumQuestion = forumRepository.findById(id);
        if(forumQuestion.isEmpty()){
            return Response.generate("forum question not found",HttpStatus.NOT_FOUND);
        }
        ForumQuestion forumQuestionNew = forumQuestion.get();
        forumQuestionNew.setQuestion(body.getQuestion());
        forumQuestionNew.setUserId(body.getUserId());
        forumQuestionNew.setVoteCount(0);

        forumRepository.save(forumQuestionNew);
        return Response.generate("question created successfully", HttpStatus.CREATED);
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
}



