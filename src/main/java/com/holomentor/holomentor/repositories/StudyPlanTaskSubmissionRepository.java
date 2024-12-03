package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.StudyPlanTaskSubmission;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public interface StudyPlanTaskSubmissionRepository extends JpaRepository<StudyPlanTaskSubmission, Long> {
    Optional<StudyPlanTaskSubmission> findById(Long Id);

    StudyPlanTaskSubmission findByTaskIdAndStudentId(Long taskId, Long userId);

    @Query("SELECT t.id AS taskId, " +
            "t.title AS taskTitle, " +
            "t.description AS taskDescription, " +
            "t.createdAt AS taskCreatedAt, " +
            "s.id AS submissionId, " +
            "s.submission AS submissionData " +
            "FROM StudyPlanTaskSubmission  s " +
            "LEFT JOIN ClassTierStudyPlanTask t " +
            "ON t.id = s.taskId " +
            "WHERE t.studyPlanId = :studyPlanId "+
            "AND s.studentId = :studentId " )
    List<Object[]> findStudyPlanTaskSubmission(
            @Param("studentId") Long studentId,
            @Param("studyPlanId") Long studyPlanId);




}