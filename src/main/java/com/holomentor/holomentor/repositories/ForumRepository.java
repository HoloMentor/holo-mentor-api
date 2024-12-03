package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.dto.forum.ForumQuestionDTO;
import com.holomentor.holomentor.models.ForumQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ForumRepository extends JpaRepository<ForumQuestion,Long> {
    Optional<ForumQuestion> findById(Long Id);

    @Query("SELECT new com.holomentor.holomentor.dto.forum.ForumQuestionDTO(fq.id, fq.classId, fq.voteCount, fq.topic, fq.subTopic, fq.question, fq.userId, u.firstName, u.lastName, u.email) " +
            "FROM ForumQuestion fq JOIN User u ON fq.userId = u.id")
    List<ForumQuestionDTO> findAllWithUserDetails();

}
