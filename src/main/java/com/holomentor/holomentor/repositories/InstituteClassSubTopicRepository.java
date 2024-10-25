package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClass;
import com.holomentor.holomentor.models.InstituteClassSubTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InstituteClassSubTopicRepository extends JpaRepository<InstituteClassSubTopic, Long> {
    Optional<InstituteClassSubTopic> findById(Long id);

    List<InstituteClassSubTopic> findAllByTopicId(Long topicId);
}
