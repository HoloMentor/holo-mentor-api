package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.ForumQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ForumRepository extends JpaRepository<ForumQuestion,Long> {
    Optional<ForumQuestion> findById(Long Id);
}
