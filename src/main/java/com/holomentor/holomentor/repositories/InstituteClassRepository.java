package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClass;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassProjection;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassTeacherProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InstituteClassRepository extends JpaRepository<InstituteClass, Long> {

    Optional<InstituteClass> findByClassName(String name);

    Optional<InstituteClass> findById(Long id);

    List<InstituteClass> findByTeacherIdAndInstituteId(Long teacherId, Long instituteId);

    @Query("SELECT s.name AS subjectName, c.className AS className, COUNT(DISTINCT cs.studentId) AS studentCount " +
            "FROM InstituteClass c " +
            "LEFT JOIN InstituteSubject s ON s.id = c.subjectId " +
            "LEFT JOIN InstituteClassStudent cs ON c.id = cs.classId " +
            "WHERE c.className ILIKE %:name% AND c.instituteId = :instituteId AND c.teacherId = :teacherId " +
            "GROUP BY s.name, c.className"
    )
    Page<InstituteClassTeacherProjection> findClassesByTeacherIdAndInstituteId(@Param("name") String name,Long teacherId, Long instituteId, Pageable pageable);


    @Query("SELECT c.id as id, " +
            "u.firstName as firstName, " +
            "u.lastName as lastName, " +
            "s.name as subjectName, " +
            "c.className as className, " +
            "COUNT(DISTINCT cs.studentId) as studentCount " +
            "FROM InstituteClass c " +
            "LEFT JOIN InstituteSubject s ON s.id = c.subjectId " +
            "LEFT JOIN InstituteClassStudent cs ON c.id = cs.classId " +
            "LEFT JOIN User u ON c.teacherId = u.id " +
            "WHERE c.className ILIKE %:name% AND s.instituteId = :instituteId " +
            "GROUP BY c.id, u.firstName, u.lastName, s.name, c.className")
    Page<InstituteClassProjection> findByClassByInstitute(@Param("name") String name, Long instituteId, Pageable pageable);
}
