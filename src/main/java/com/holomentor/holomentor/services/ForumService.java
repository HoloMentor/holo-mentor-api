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


        ForumQuestion forumQuestion = new ForumQuestion();
        forumQuestion.setClassId(body.getClassId());
        forumQuestion.setTopic(body.getTopic());
        forumQuestion.setSubTopic(body.getSubTopic());
        forumQuestion.setQuestion(body.getQuestion());
        forumQuestion.setUserId(body.getUserId());
        forumQuestion.setMcqAnswer(body.getAnswersMcq());
        forumQuestion.setVoteCount(0);
        System.out.println(body.getQuestion());
        forumQuestion.setQuestion(body.getQuestion());

        System.out.println("here's creates forumquestion model: >>>: "+forumQuestion);


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

//    public String extractTextContent(Object questionData) {
//        StringBuilder plainTextQuestion = new StringBuilder();
//
//        // Assuming questionData is a List<Map> instead of JSON String
//        List<Map<String, Object>> questionList = (List<Map<String, Object>>) questionData;
//
//        for (Map<String, Object> paragraph : questionList) {
//            if ("paragraph".equals(paragraph.get("type"))) {
//                List<Map<String, Object>> contentList = (List<Map<String, Object>>) paragraph.get("content");
//                for (Map<String, Object> contentItem : contentList) {
//                    if ("text".equals(contentItem.get("type"))) {
//                        plainTextQuestion.append(contentItem.get("text")).append(" ");
//                    }
//                }
//            }
//        }
//        return plainTextQuestion.toString().trim();
//    }

}



