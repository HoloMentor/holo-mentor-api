package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClassStudent;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassStudentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
@Transactional
public interface InstituteClassStudentRepository extends JpaRepository<InstituteClassStudent, Long> {
    List<InstituteClassStudent> findByClassId(Long classId);

    @Query("select u.id as id , u.firstName as firstName, u.lastName as lastName "+
            "FROM User u "+
            "LEFT JOIN InstituteClassStudent ics ON ics.id = u.id "+
            "WHERE ics.classId = :classId " )
    Page<InstituteClassStudentProjection> findStudentsByClassId(Long classId, Pageable pageable);
}