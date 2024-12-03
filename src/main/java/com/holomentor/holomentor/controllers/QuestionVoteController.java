package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.votes.ForumVoteDTO;
import com.holomentor.holomentor.models.QuestionVotes;
import com.holomentor.holomentor.services.QuestionVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("forum/votes")
public class QuestionVoteController {

    @Autowired
    private QuestionVoteService questionVoteService;
    @PostMapping("/{id}")
    public ResponseEntity<Object> castVote(@PathVariable("id") Long questionId, @RequestBody ForumVoteDTO body) throws IOException{
        System.out.println("\n\n\n"+ body + "\n\n\n");
        System.out.println(body.getVoteType());
        return questionVoteService.addorUpdateVote(questionId, body.getUserId(), body.getVoteType());
    }


    @GetMapping("/{id}/all")
    public ResponseEntity<Object> getTotalVotesForQuestion(@PathVariable("id") Long questionId) {
        int totalVotes = questionVoteService.getTotalVotesForQuestion(questionId);
        return ResponseEntity.ok(Map.of("totalVotes", totalVotes));
    }
}
