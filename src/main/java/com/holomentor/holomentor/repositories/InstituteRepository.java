package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.projections.institute.InstituteDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Long> {
    @Query( value = "SELECT i.id AS id, " +
            "       i.name AS instituteName, " +
            "       i.logo AS instituteImage, " +
            "       i.city AS instituteCity, " +
            "       COUNT(DISTINCT ic.id) AS classCount, " +
            "       COUNT(DISTINCT ics.id) AS studentCount, " +
            "       COUNT(DISTINCT ui.id) AS teacherCount " +
            "FROM Institute i " +
            "LEFT JOIN InstituteClass ic ON i.id = ic.instituteId " +
            "LEFT JOIN InstituteClassStudent ics ON ic.id = ics.classId " +
            "LEFT JOIN UserInstitute ui ON ic.teacherId = ui.id AND ui.role = 'TEACHER' " +
            "WHERE i.isDeleted = :isDeleted " +
            "AND i.name LIKE %:name% " +
            "GROUP BY i.id, i.name, i.logo, i.city",
    countQuery = "SELECT COUNT(*) FROM Institute i WHERE i.isDeleted = false AND i.name LIKE %:name%")
    Page<InstituteDetailsProjection> getInstituteDetailsByNameAndIsDeleted(
            String name,
            Boolean isDeleted, Pageable pageable);

    Page<Institute> findByNameContainingIgnoreCaseAndIsDeleted(String name, Boolean isDeleted, Pageable pageable);
}
