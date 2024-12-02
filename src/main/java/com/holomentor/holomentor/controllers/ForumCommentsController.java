package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.forumComment.ForumCommentDTO;
import com.holomentor.holomentor.services.ForumCommentService;
import com.holomentor.holomentor.utils.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("comments")
public class ForumCommentsController {

    @Autowired
    private ForumCommentService forumCommentService;

    @PostMapping("/add")
    public ResponseEntity<Object> addComment(@Valid @RequestBody ForumCommentDTO body) throws IOException{
        return forumCommentService.addComment(body);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteComment(@PathVariable @Valid Long id) throws IOException{
        return forumCommentService.deleteComment(id);
    }
}
