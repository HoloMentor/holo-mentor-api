package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.forum.ForumQuestionCreateDTO;
import com.holomentor.holomentor.services.ForumService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("forum")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @PostMapping("/mcq/create")
    public ResponseEntity<Object> createMcq (@Valid @RequestBody ForumQuestionCreateDTO body) throws IOException {
        System.out.println(body);
        return forumService.createMcq(body);
    };

}
