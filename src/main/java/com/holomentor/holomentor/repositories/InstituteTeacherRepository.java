package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClass;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
