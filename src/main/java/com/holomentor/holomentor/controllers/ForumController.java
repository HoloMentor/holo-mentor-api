package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.forum.ForumQuestionCreateDTO;
import com.holomentor.holomentor.services.ForumService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable @Valid Long id) throws IOException   {
        return forumService.get(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() throws IOException{
        return forumService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Valid Long id)throws IOException{
        return forumService.delete(id);
    }

}
