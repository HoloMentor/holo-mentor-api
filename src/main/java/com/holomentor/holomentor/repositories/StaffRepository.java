package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.models.InstituteSubject;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassProjection;
import com.holomentor.holomentor.projections.subject.InstituteSubjectProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface StaffRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Page<InstituteSubject> findByNameContainingIgnoreCaseAndInstituteId(String name, Long instituteId, Pageable pageable);


    @Query("SELECT s.id as id, s.name as name, s.instituteId as instituteId, s.createdAt as createdAt, COUNT(DISTINCT c.id) AS classCount, COUNT(DISTINCT cs.studentId) AS studentCount " +
            "FROM InstituteSubject s " +
            "LEFT JOIN InstituteClass c ON s.id = c.subjectId " +
            "LEFT JOIN InstituteClassStudent cs ON c.id = cs.classId " +
            "WHERE s.name ILIKE %:name% AND s.instituteId = :instituteId " +
            "GROUP BY s.id, s.name")
    Page<InstituteClassProjection> findByClassByInstitute(String name, Long instituteId, Pageable pageable);

}