package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClassStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface InstituteClassStudentRepository extends JpaRepository<InstituteClassStudent, Long> {
    List<InstituteClassStudent> findByClassId(Long classId);
}
