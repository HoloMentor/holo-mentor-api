package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ForumCommentRepository extends JpaRepository<ForumComment,Long> {
    Optional<ForumComment> findById(Long Id);
}
