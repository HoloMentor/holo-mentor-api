package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.forumComment.ForumCommentDTO;
import com.holomentor.holomentor.models.ForumComment;
import com.holomentor.holomentor.repositories.ForumCommentRepository;
import com.holomentor.holomentor.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ForumCommentService {
    @Autowired
    private ForumCommentRepository forumCommentRepository;

    public ResponseEntity<Object> addComment(ForumCommentDTO body) throws IOException{
        ForumComment forumComment = new ForumComment();
        forumComment.setQuestionId(body.getQuestionId());
        forumComment.setUserId(body.getUserId());
        forumComment.setComment(body.getCommentText());
        forumComment.setCreatedAt(LocalDateTime.now());
        forumCommentRepository.save(forumComment);
        return Response.generate("comment added successfully", HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteComment(Long id) throws IOException{
        Optional<ForumComment> forumComment = forumCommentRepository.findById(id);
        if(forumComment.isEmpty()){
            return Response.generate("Comment Not found",HttpStatus.NOT_FOUND);
        }
        else{
            ForumComment forumCommentdelete = forumComment.get();
            forumCommentRepository.delete(forumCommentdelete);
            return Response.generate("Comment deleted successfully",HttpStatus.OK);
        }
    }
}
