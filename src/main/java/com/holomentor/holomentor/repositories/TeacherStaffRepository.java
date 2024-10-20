// TeacherStaffRepository.java
package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.TeacherStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherStaffRepository extends JpaRepository<TeacherStaff, Long> {

    // Find all TeacherStaff records by institute ID and teacher ID
    List<TeacherStaff> findAllByInstituteIdAndUserTeacherId(Long instituteId, Long userTeacherId);

    // Find a TeacherStaff record by its ID
    Optional<TeacherStaff> findById(Long id);

    // Find all TeacherStaff records marked as not deleted (isDelete = false)
    List<TeacherStaff> findAllByIsDeleteFalse();

    // Custom query to find TeacherStaff by userStaffId and instituteId, if needed
    Optional<TeacherStaff> findByUserStaffIdAndInstituteId(Long userStaffId, Long instituteId);

    // Find all TeacherStaff records for a specific userStaffId
    List<TeacherStaff> findAllByUserStaffId(Long userStaffId);
}
