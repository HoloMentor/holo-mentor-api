package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.projections.teacher.InstituteTeacherProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInstituteRepository extends JpaRepository<UserInstitute, Long> {
    List<UserInstitute> findByInstituteId(Long id);

    List<UserInstitute> findByUserId(Long id);

    List<UserInstitute> findByUserEmail(String email);

    Optional<UserInstitute> findByUserIdAndInstituteId(Long id, Long instituteId);

    Optional<UserInstitute> findByUserIdAndInstituteIdAndRole(Long id, Long instituteId, UserInstitute.RoleTypes role);

    List<UserInstitute> findByRoleAndInstituteId(UserInstitute.RoleTypes role, Long instituteId);

    @Query("SELECT s.userId as id, u.firstName as firstName, u.lastName as lastName, u.image as image " +
            "FROM UserInstitute s " +
            "LEFT JOIN User u ON s.userId = u.id " +
            "WHERE (u.firstName ILIKE %:name% OR u.lastName ILIKE %:name%) " +
            "AND s.instituteId = :instituteId " +
            "AND s.isActive = :isActive " +
            "AND s.role = 'TEACHER'")
    Page<InstituteTeacherProjection> findByInstituteIdTeachersAndIsActive(String name, Long instituteId,
            Boolean isActive, Pageable pageable);

}