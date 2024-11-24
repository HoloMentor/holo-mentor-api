package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.ClassTierStudyPlan;
import com.holomentor.holomentor.models.ClassTierStudyPlanTask;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.projections.teacher.InstituteTeacherProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassTierStudyPlanRepository extends JpaRepository<ClassTierStudyPlan, Long> {
    Optional<ClassTierStudyPlan> findById(Long id);

    List<ClassTierStudyPlan> getByClassIdAndTier(Long classId, Integer tier);

    @Query("SELECT sp FROM ClassTierStudyPlan sp LEFT JOIN FETCH sp.tasks WHERE sp.classId = :classId AND sp.tier = :tier")
    List<ClassTierStudyPlan> findByClassIdAndTierWithTasks(Long classId, Integer tier);
}