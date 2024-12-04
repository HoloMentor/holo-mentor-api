package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClass;
import com.holomentor.holomentor.projections.instituteClass.ClassDetailsProjection;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassProjection;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassStudentCountProjection;
import com.holomentor.holomentor.projections.teacher.InstituteTeacherClassProjection;
import com.holomentor.holomentor.projections.student.InstituteStudentClassProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InstituteClassRepository extends JpaRepository<InstituteClass, Long> {

    Optional<InstituteClass> findById(Long id);

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
    Page<InstituteClassProjection> findByClassByInstitute(String name, Long instituteId, Pageable pageable);

    Page<InstituteTeacherClassProjection> findInstituteClassesByInstituteTeacherId(Long teacherId, Pageable pageable);

    @Query("SELECT s.name as subjectName, " +
            "c.className as className, " +
            "u.firstName as firstName, " +
            "u.lastName as lastName, " +
            "COUNT(DISTINCT cs.studentId) as studentCount, " +
            "c.dayOfWeek as dayOfWeek, " +
            "c.startTime as startTime, " +
            "c.endTime as endTime " +
            "FROM InstituteClass c " +
            "LEFT JOIN InstituteSubject s ON s.id = c.subjectId " +
            "LEFT JOIN InstituteClassStudent cs ON c.id = cs.classId " +
            "LEFT JOIN User u ON c.teacherId = u.id " +
            "WHERE s.instituteId = :instituteId AND c.teacherId = :teacherId " +
            "GROUP BY s.name, c.className, u.firstName, u.lastName, c.dayOfWeek, c.startTime, c.endTime")
    List<InstituteClassProjection> findClassesGroupedByTeacherAndInstitute(Long teacherId, Long instituteId);

    @Query("SELECT c.id as id, " +
            "c.className as className, " +
            "s.name as subjectName, " +
            "c.teacherId as teacherId, " +
            "u.firstName as teacherFirstName, " +
            "u.lastName as teacherLastName, " +
            "COUNT(DISTINCT cs.studentId) as studentCount " +
            "FROM InstituteClass c " +
            "LEFT JOIN InstituteSubject s ON s.id = c.subjectId " +
            "LEFT JOIN InstituteClassStudent cs ON c.id = cs.classId " +
            "LEFT JOIN User u ON c.teacherId = u.id " +
            "WHERE cs.studentId = :studentId " +
            "GROUP BY c.id, c.className, s.name, u.firstName, u.lastName")
    Page<InstituteStudentClassProjection> findClassesByStudentId(Long studentId, Pageable pageable);

    @Query("SELECT ic.className AS className, COUNT(ics.studentId) AS studentCount " +
            "FROM InstituteClassStudent ics " +
            "JOIN InstituteClass ic ON ics.classId = ic.id " +
            "WHERE ics.instituteId = :instituteId AND ic.teacherId = :teacherId " +
            "GROUP BY ic.className")
    List<InstituteClassStudentCountProjection> findStudentCountByClass(Long teacherId, Long instituteId);

    @Query( "SELECT ic.id AS classId, " +
            "       ic.className AS className, " +
            "       t.firstName AS teacherFirstName, " +
            "       t.lastName AS teacherLastName, " +
            "       i.name AS instituteName, " +
            "       COUNT(ics.id) AS studentCount " +
            "FROM InstituteClass ic " +
            "LEFT JOIN User t ON ic.teacherId = t.id " +
            "LEFT JOIN Institute i ON ic.instituteId = i.id " +
            "LEFT JOIN InstituteClassStudent ics ON ic.id = ics.classId " +
            "GROUP BY ic.id, ic.className, t.firstName, t.lastName, i.name " +
            "ORDER BY studentCount DESC")
    List<ClassDetailsProjection> getAllClassesWithTeacherInstituteAndStudentCount();


}
