package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.dto.forumComment.ForumCommentWithUserDTO;
import com.holomentor.holomentor.models.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ForumCommentRepository extends JpaRepository<ForumComment,Long> {
    Optional<ForumComment> findById(Long Id);

    List<ForumComment> findByQuestionId(Long questionId);

    @Query("SELECT new com.holomentor.holomentor.dto.forumComment.ForumCommentWithUserDTO(fc.questionId, fc.userId, fc.comment, fc.createdAt, u.firstName, u.lastName) " +
            "FROM ForumComment fc JOIN User u ON fc.userId = u.id WHERE fc.questionId = :questionId")
    List<ForumCommentWithUserDTO> findCommentsByQuestionId(@Param("questionId") Long questionId);


}
