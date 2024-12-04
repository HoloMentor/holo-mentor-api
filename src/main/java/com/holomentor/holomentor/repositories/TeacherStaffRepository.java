// TeacherStaffRepository.java
package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.TeacherStaff;
import com.holomentor.holomentor.projections.user.UserInstituteProjection;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherStaffRepository extends JpaRepository<TeacherStaff, Long> {

    // Find all TeacherStaff records by institute ID and teacher ID
    Page<TeacherStaff> findAllByInstituteIdAndUserTeacherId(Long instituteId, Long teacherId, Pageable pageable);

    // Find a TeacherStaff record by its ID
    Optional<TeacherStaff> findById(Long id);

    // Find all TeacherStaff records marked as not deleted (isDelete = false)
    List<TeacherStaff> findAllByIsDeleteFalse();

    // Custom query to find TeacherStaff by userStaffId and instituteId, if needed
    Optional<TeacherStaff> findByUserStaffIdAndInstituteId(Long userStaffId, Long instituteId);

    @Query("SELECT iu.id as userInstituteId, " +
            "iu.userId as userId, " +
            "iu.instituteId as instituteId, " +
            "its.id as instituteTeacherStaffId, " +
            "iu.role as role, " +
            "iu.isActive as isActive, " +
            "its.isDelete as isDelete, " +
            "its.createdAt as createdAt " +
            "FROM UserInstitute iu " +
            "JOIN TeacherStaff its ON iu.userId = its.userTeacherId " +
            "AND iu.instituteId = its.instituteId " +
            "WHERE its.userStaffId = :userId " +
            "AND its.instituteId = :instituteId")
    List<UserInstituteProjection> findDetailsByUserIdAndInstituteId(Long userId, Long instituteId);

    // Find all TeacherStaff records for a specific userStaffId
    List<TeacherStaff> findAllByUserStaffId(Long userStaffId);

    @Query("SELECT COUNT(DISTINCT ts.id) " +
            "FROM TeacherStaff ts " +
            "WHERE ts.userTeacherId = :teacherId " +
            "AND ts.instituteId = :instituteId " +
            "AND ts.isDelete = false")
    long countTeacherStaff(Long teacherId,Long instituteId);

}
