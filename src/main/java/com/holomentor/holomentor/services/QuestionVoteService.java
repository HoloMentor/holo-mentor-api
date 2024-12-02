package com.holomentor.holomentor.services;

import com.holomentor.holomentor.models.QuestionVotes;
import com.holomentor.holomentor.repositories.QustionVotertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.holomentor.holomentor.utils.Response;

import java.io.IOException;

@Service
public class QuestionVoteService {

    @Autowired
    private QustionVotertRepository qustionVotertRepository;

    public ResponseEntity<Object> addorUpdateVote(Long questionId, Long userId, QuestionVotes.VoteTypes votetype) throws IOException {
        QuestionVotes existingVote = qustionVotertRepository.findByQuestionIdAndUserId(questionId, userId);
        System.out.println(existingVote);
        if (existingVote != null) {
            if ((existingVote.getVoteType() == QuestionVotes.VoteTypes.VOTE_DOWN && votetype == QuestionVotes.VoteTypes.VOTE_UP) || (existingVote.getVoteType() == QuestionVotes.VoteTypes.VOTE_UP && votetype == QuestionVotes.VoteTypes.VOTE_DOWN)) {
                existingVote.setVoteType(QuestionVotes.VoteTypes.NONE);
                qustionVotertRepository.save(existingVote);
                return Response.generate("Vote Updated", HttpStatus.OK);
            } else if (existingVote.getVoteType() == QuestionVotes.VoteTypes.NONE) {
                existingVote.setVoteType(votetype);
                qustionVotertRepository.save(existingVote);
                return Response.generate("Vote Updated", HttpStatus.OK);
            } else {
                return Response.generate("Nothing to update", HttpStatus.OK);
            }

        } else {
            QuestionVotes newVote = new QuestionVotes();
            newVote.setQuestionId(questionId);
            newVote.setUserId(userId);
            newVote.setVoteType(votetype);
            qustionVotertRepository.save(newVote);
            return Response.generate("Added vote", HttpStatus.OK);
        }


    }

    public int getTotalVotesForQuestion(Long questionId) {
        Integer totalVotes = qustionVotertRepository.calculateTotalVotesByQuestionId(questionId);
        // Handle null (if no votes are present for the question)
        return totalVotes != null ? totalVotes : 0;
    }

}
