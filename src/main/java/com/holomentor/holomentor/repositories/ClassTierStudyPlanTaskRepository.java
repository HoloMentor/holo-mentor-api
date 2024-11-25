package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.ClassTierStudyPlan;
import com.holomentor.holomentor.models.ClassTierStudyPlanTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassTierStudyPlanTaskRepository extends JpaRepository<ClassTierStudyPlanTask, Long> {
    Optional<ClassTierStudyPlanTask> findById(Long id);

    List<ClassTierStudyPlanTask> findByClassIdAndStudyPlanId(Long classId, Long studyPlanId);

}