package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.StudyPlanTaskSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public interface StudyPlanTaskSubmissionRepository extends JpaRepository<StudyPlanTaskSubmission, Long> {
    Optional<StudyPlanTaskSubmission> findById(Long Id);

    StudyPlanTaskSubmission findByTaskIdAndStudentId(Long taskId, Long userId);

}