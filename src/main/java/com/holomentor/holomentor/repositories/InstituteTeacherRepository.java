package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.UserInstitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface InstituteTeacherRepository extends JpaRepository<UserInstitute, Long> {

    @Query("select COUNT(DISTINCT ic.id) as classCount, " +
            "COUNT(DISTINCT ics.studentId) as studentCount " +
            "FROM InstituteClass ic " +
            "LEFT JOIN InstituteClassStudent ics ON ics.classId = ic.id " +
            "WHERE ic.teacherId = :teacherId ")
    InstituteTeacherStatProjection findTeacherStatsById(Long teacherId);
}
