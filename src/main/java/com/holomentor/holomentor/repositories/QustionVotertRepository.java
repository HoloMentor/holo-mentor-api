package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.QuestionVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QustionVotertRepository extends JpaRepository <QuestionVotes,Long>{

    QuestionVotes findByQuestionIdAndUserId(Long questionId, Long userId);

    @Query("SELECT SUM(CASE " +
            "WHEN v.voteType = 'VOTE_UP' THEN 1 " +
            "WHEN v.voteType = 'VOTE_DOWN' THEN -1 " +
            "ELSE 0 END) " +
            "FROM QuestionVotes v WHERE v.questionId = :questionId")
    Integer calculateTotalVotesByQuestionId(@Param("questionId") Long questionId);

}
